package com.feature.expenses.ui.screens.expenses_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.feature.expenses.domain.usecase.GetExpensesForPeriodUseCase
import com.feature.expenses.ui.models.ExpensesHistoryUiModel
import com.feature.expenses.ui.models.formatExpenseDate
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayViewModel
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

import com.core.domain.usecase.GetCurrencyUseCase
import com.core.domain.models.TransactionDomainModel

/**
 * Тут лежит сама ViewModel
 */
class ExpensesHistoryViewModel @Inject constructor(
    private val getExpensesForPeriodUseCase: GetExpensesForPeriodUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
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
            try {
                val currency = getCurrencyUseCase().getOrThrow()
                val listOfExpenses =
                    getExpensesForPeriodUseCase.invoke(startDate, endDate, accountId = 211)
                        .getOrThrow()
                _expensesHistoryScreenState.value = ExpensesHistoryScreenState.Loaded(
                    data = toHistoryScreenData(listOfExpenses, startDate, endDate, currency)
                )
            } catch (e: Exception) {
                _expensesHistoryScreenState.value = ExpensesHistoryScreenState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun toHistoryScreenData(
        listOfExpenses: List<TransactionDomainModel>,
        startDate: String,
        endDate: String,
        currency: String
    ): HistoryScreenData {
        val total = listOfExpenses.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
        return HistoryScreenData(
            expenses = listOfExpenses.map {
                ExpensesHistoryUiModel(
                    emojiData = it.category.emoji,
                    name = it.category.name,
                    description = it.comment,
                    amount = it.amount,
                    time = it.transactionDate.formatExpenseDate(),
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
class ExpensesHistoryViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<ExpensesHistoryViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}