package com.feature.expenses.ui.screens.expenses_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import com.feature.expenses.ui.models.ExpensesUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Тут лежит сама ViewModel
 */
class ExpensesTodayViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase
) : ViewModel() {

    private var _expensesTodayScreenState: MutableStateFlow<ExpensesTodayScreenState> =
        MutableStateFlow(ExpensesTodayScreenState.Loading)
    val expensesTodayScreenState = _expensesTodayScreenState.asStateFlow()

    init {
        fetchTodayExpenses(null)
    }

    fun fetchTodayExpenses(date: String?) {
        viewModelScope.launch {
            _expensesTodayScreenState.value = ExpensesTodayScreenState.Loading
            getTodayExpensesUseCase.invoke()
                .onSuccess { listOfExpenses ->
                    val total = listOfExpenses.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }.toString()
                    _expensesTodayScreenState.value = ExpensesTodayScreenState.Loaded(
                        data = ExpensesScreenData(
                            expenses = listOfExpenses.map {
                                ExpensesUiModel(
                                    id = it.id,
                                    icon = it.category.emoji,
                                    category = it.category.name,
                                    amount = it.amount,
                                    comment = it.comment
                                )
                            },
                            totalAmount = "$total R"
                        )
                    )
                }
                .onFailure { error ->
                    _expensesTodayScreenState.value = ExpensesTodayScreenState.Error(
                        message = error.message ?: "Unknown error"
                    )
                }
        }
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