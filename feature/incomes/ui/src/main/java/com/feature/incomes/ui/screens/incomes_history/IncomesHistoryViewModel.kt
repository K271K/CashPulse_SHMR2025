package com.feature.incomes.ui.screens.incomes_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.models.TransactionDomainModel
import com.core.domain.usecase.GetCurrencyUseCase
import com.feature.incomes.domain.usecase.GetIncomesForPeriodUseCase
import com.feature.incomes.ui.models.IncomesHistoryUiModel
import com.feature.incomes.ui.models.formatIncomeDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Provider

/**
 * Тут лежит сама ViewModel
 */
class IncomesHistoryViewModel @Inject constructor(
    private val getIncomesForPeriodUseCase: GetIncomesForPeriodUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel(){
    private val _incomesHistoryScreenState: MutableStateFlow<IncomesHistoryScreenState> =
        MutableStateFlow(IncomesHistoryScreenState.Loading)
    val incomesHistoryScreenState = _incomesHistoryScreenState.asStateFlow()

    private var currentStartDate: String = ""
    private var currentEndDate: String = ""

    init {
        setDefaultMonthDates()
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

    private fun fetchIncomesForPeriod(startDate: String, endDate: String) {
        viewModelScope.launch {
            _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loading
            try {
                val currency = getCurrencyUseCase().getOrThrow()
                val listOfExpenses =
                    getIncomesForPeriodUseCase.invoke(startDate, endDate, accountId = 211)
                        .getOrThrow()
                _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loaded(
                    data = toHistoryScreenData(listOfExpenses, startDate, endDate, currency)
                )
            } catch (e: Exception) {
                _incomesHistoryScreenState.value = IncomesHistoryScreenState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun toHistoryScreenData(
        listOfIncomes: List<TransactionDomainModel>,
        startDate: String,
        endDate: String,
        currency: String
    ): HistoryScreenData {
        val total = listOfIncomes.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
        return HistoryScreenData(
            incomes = listOfIncomes.map {
                IncomesHistoryUiModel(
                    emojiData = it.category.emoji,
                    name = it.category.name,
                    description = it.comment,
                    amount = it.amount,
                    time = it.transactionDate.formatIncomeDate(),
                    currency = currency,
                    id = it.id
                )
            },
            totalAmount = "$total $currency",
            startDate = startDate,
            endDate = endDate
        )
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class IncomesHistoryViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<IncomesHistoryViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}
