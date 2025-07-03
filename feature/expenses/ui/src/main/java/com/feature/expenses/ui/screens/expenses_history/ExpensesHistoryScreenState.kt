package com.feature.expenses.ui.screens.expenses_history

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.feature.expenses.ui.models.ExpensesHistoryUiModel

sealed interface ExpensesHistoryScreenState {

    data object Loading : ExpensesHistoryScreenState

    data class Error(val message: String) : ExpensesHistoryScreenState

    data class ErrorResource(@StringRes val messageId: Int) :
        ExpensesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : ExpensesHistoryScreenState
}

@Immutable
data class HistoryScreenData(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<ExpensesHistoryUiModel>
)