package com.feature.expenses.ui.screens.expenses_add

import com.core.ui.models.CategoryPickerUiModel

data class AddExpenseUiState(
    val categories: List<CategoryPickerUiModel> = emptyList(),
    val selectedCategory: CategoryPickerUiModel? = null,
    val amount: String = "",
    val date: String = "",
    val time: String = "",
    val comment: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
