package com.feature.expenses.ui.screens.expenses_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.core.domain.models.TransactionDomainModel
import com.core.domain.usecase.GetCurrencyUseCase
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import com.feature.expenses.ui.models.ExpensesUiModel
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Тут лежит сама ViewModel
 */
class ExpensesTodayViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private var _expensesTodayScreenState: MutableStateFlow<ExpensesTodayScreenState> =
        MutableStateFlow(ExpensesTodayScreenState.Loading)
    val expensesTodayScreenState = _expensesTodayScreenState.asStateFlow()

    init {
        fetchTodayExpenses()
    }

    private fun fetchTodayExpenses() {
        viewModelScope.launch {
            _expensesTodayScreenState.value = ExpensesTodayScreenState.Loading
            try {
                val currency = getCurrencyUseCase().getOrThrow()
                val listOfExpenses = getTodayExpensesUseCase().getOrThrow()
                _expensesTodayScreenState.value = ExpensesTodayScreenState.Loaded(
                    data = toScreenData(listOfExpenses, currency)
                )
            } catch (e: Exception) {
                _expensesTodayScreenState.value = ExpensesTodayScreenState.Error(
                    message = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun toScreenData(
        listOfExpenses: List<TransactionDomainModel>,
        currency: String
    ): ExpensesScreenData {
        val total = listOfExpenses.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
        return ExpensesScreenData(
            expenses = listOfExpenses.map {
                ExpensesUiModel(
                    id = it.id,
                    icon = it.category.emoji,
                    category = it.category.name,
                    amount = it.amount,
                    comment = it.comment,
                    currency = currency
                )
            },
            totalAmount = total,
            currency = currency
        )
    }
}

/**
 * Тут лежит фабрика для ViewModel. Мне кажется так проще в коде ориентироваться,
 * не вижу смысла отдельную папку сувать viewModels и в отдельную папку сувать фабрики для них
 */
class ExpensesTodayViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<ExpensesTodayViewModel>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}