package com.feature.incomes.ui.screens.incomes_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.incomes.domain.usecase.GetIncomesForPeriodUseCase
import com.feature.incomes.ui.models.IncomesHistoryUiModel
import com.feature.incomes.ui.models.formatIncomeDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class IncomesHistoryViewModel @Inject constructor(
    private val getIncomesForPeriodUseCase: GetIncomesForPeriodUseCase
) : ViewModel(){
    private val _incomesHistoryScreenState: MutableStateFlow<IncomesHistoryScreenState> =
        MutableStateFlow(IncomesHistoryScreenState.Loading)
    val incomesHistoryScreenState = _incomesHistoryScreenState.asStateFlow()

    private var currentStartDate: String = ""
    private var currentEndDate: String = ""

    init {
        setDefaultMonthDates()
    }

    fun updateStartDate(selectedDate: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        currentStartDate = sdf.format(Date(selectedDate))

        if (currentEndDate.isNotEmpty()) {
            fetchIncomesForPeriod(
                startDate = currentStartDate,
                endDate = currentEndDate
            )
        }
    }

    fun updateEndDate(selectedDate: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        currentEndDate = sdf.format(Date(selectedDate))

        if (currentStartDate.isNotEmpty()) {
            fetchIncomesForPeriod(
                startDate = currentStartDate,
                endDate = currentEndDate
            )
        }
    }

    private fun setDefaultMonthDates() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        // Начало текущего месяца
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startDate = sdf.format(calendar.time)
        // Конец текущего месяца
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endDate = sdf.format(calendar.time)

        currentStartDate = startDate
        currentEndDate = endDate

        fetchIncomesForPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun fetchIncomesForPeriod(startDate: String, endDate: String) {
        viewModelScope.launch {
            _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loading
            getIncomesForPeriodUseCase.invoke(
                startDate = startDate,
                endDate = endDate
            )
                .onSuccess { listOfIncomes ->
                    val totalAmount = listOfIncomes.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
                    _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loaded(
                        data = HistoryScreenData(
                            listOfIncomes = listOfIncomes.map { domainIncomeModel ->
                                IncomesHistoryUiModel(
                                    id = domainIncomeModel.id,
                                    emojiData = domainIncomeModel.category.emoji,
                                    name = domainIncomeModel.category.name,
                                    description = domainIncomeModel.comment,
                                    amount = domainIncomeModel.amount,
                                    time = domainIncomeModel.transactionDate.formatIncomeDate()
                                )
                            },
                            startDate = startDate,
                            endDate = endDate,
                            totalAmount = "$totalAmount R",
                        )
                    )
                }
                .onFailure { error->
                    _incomesHistoryScreenState.value = IncomesHistoryScreenState.Error(
                        message = error.message ?: "Unknown error"
                    )
                }
        }
    }
}
