package com.feature.incomes.ui.screens.incomes_history

import androidx.compose.runtime.Immutable
import com.feature.incomes.ui.models.IncomesHistoryUiModel

sealed interface IncomesHistoryScreenState {

    data object Loading : IncomesHistoryScreenState

    data class Error(val message: String) : IncomesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : IncomesHistoryScreenState
}

@Immutable
data class HistoryScreenData(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val incomes: List<IncomesHistoryUiModel>,
)