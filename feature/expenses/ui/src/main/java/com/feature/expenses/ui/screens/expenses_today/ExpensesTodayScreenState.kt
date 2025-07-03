package com.feature.expenses.ui.screens.expenses_today

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.feature.expenses.ui.models.ExpensesUiModel

@Immutable
interface ExpensesTodayScreenState {

    data object Loading: ExpensesTodayScreenState

    data class Loaded(
        val data: ExpensesScreenData
    ): ExpensesTodayScreenState

    data class Error(
        val message: String
    ) : ExpensesTodayScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : ExpensesTodayScreenState
}

@Immutable
data class ExpensesScreenData(
    val expenses: List<ExpensesUiModel>,
    val totalAmount: String
)