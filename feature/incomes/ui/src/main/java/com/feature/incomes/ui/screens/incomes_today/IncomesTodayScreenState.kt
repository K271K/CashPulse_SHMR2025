package com.feature.incomes.ui.screens.incomes_today

import androidx.compose.runtime.Immutable
import com.feature.incomes.ui.models.IncomesUiModel

@Immutable
interface IncomesTodayScreenState {

    data object Loading: IncomesTodayScreenState

    data class Loaded(
        val data: IncomesTodayScreenData
    ): IncomesTodayScreenState

    data class Error(
        val message: String
    ): IncomesTodayScreenState
}

@Immutable
data class IncomesTodayScreenData(
    val incomes: List<IncomesUiModel>,
    val totalAmount: String
)