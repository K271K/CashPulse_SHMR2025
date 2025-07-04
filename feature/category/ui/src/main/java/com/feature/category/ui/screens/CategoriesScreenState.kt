package com.feature.category.ui.screens

import androidx.compose.runtime.Immutable
import com.feature.category.ui.models.CategoryUiModel

@Immutable
interface CategoriesScreenState {

    data object Loading: CategoriesScreenState

    data class Loaded(
        val data: CategoriesScreenData
    ): CategoriesScreenState

    data class Error(
        val message: String
    ): CategoriesScreenState
}

@Immutable
data class CategoriesScreenData(
    val categories: List<CategoryUiModel>,
    val filteredCategories: List<CategoryUiModel> = emptyList()
)