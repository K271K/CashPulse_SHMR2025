package com.feature.expenses.ui.screens.expenses_today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import com.feature.expenses.ui.models.ExpensesUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
            getTodayExpensesUseCase.invoke(date = date)
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