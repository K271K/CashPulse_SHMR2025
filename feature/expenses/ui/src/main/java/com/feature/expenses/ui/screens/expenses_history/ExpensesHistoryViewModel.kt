package com.feature.expenses.ui.screens.expenses_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.expenses.domain.usecase.GetExpensesForPeriodUseCase
import com.feature.expenses.ui.models.ExpensesHistoryUiModel
import com.feature.expenses.ui.models.formatExpenseDate
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
class ExpensesHistoryViewModel @Inject constructor(
    private val getExpensesForPeriodUseCase: GetExpensesForPeriodUseCase
) : ViewModel() {

    private val _expensesHistoryScreenState: MutableStateFlow<ExpensesHistoryScreenState> =
        MutableStateFlow(ExpensesHistoryScreenState.Loading)
    val historyScreenState = _expensesHistoryScreenState.asStateFlow()

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

        fetchExpensesForPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }

    fun updateStartDate(selectedDate: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        currentStartDate = sdf.format(Date(selectedDate))

        if (currentEndDate.isNotEmpty()) {
            fetchExpensesForPeriod(
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
            fetchExpensesForPeriod(
                startDate = currentStartDate,
                endDate = currentEndDate
            )
        }
    }

    private fun fetchExpensesForPeriod(startDate: String, endDate: String) {
        viewModelScope.launch {
            _expensesHistoryScreenState.value = ExpensesHistoryScreenState.Loading
            getExpensesForPeriodUseCase.invoke(startDate, endDate)
                .onSuccess { listOfExpenses->
                    val total = listOfExpenses.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
                    _expensesHistoryScreenState.value = ExpensesHistoryScreenState.Loaded(
                        data = HistoryScreenData(
                            expenses = listOfExpenses.map {
                                ExpensesHistoryUiModel(
                                    emojiData = it.category.emoji,
                                    name = it.category.name,
                                    description = it.comment,
                                    amount = it.amount,
                                    time = it.transactionDate.formatExpenseDate()
                                )
                            },
                            totalAmount = "$total R",
                            startDate = startDate,
                            endDate = endDate
                        )
                    )
                }
                .onFailure { error ->
                    _expensesHistoryScreenState.value = ExpensesHistoryScreenState.Error(
                        message = error.message ?: "Unknown error"
                    )
                }
        }
    }
}