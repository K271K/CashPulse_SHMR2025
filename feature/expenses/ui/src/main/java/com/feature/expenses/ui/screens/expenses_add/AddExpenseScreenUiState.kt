package com.feature.expenses.ui.screens.expenses_add

import com.core.ui.models.CategoryPickerUiModel

data class AddExpenseScreenUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryPickerUiModel> = emptyList(),
    val selectedCategory: CategoryPickerUiModel? = null,
    val currency: String = "",
    val accountName: String = "",
    val categoryName: String = "",
    val amount: String = "",
    val expenseDate: String = "",
    val expenseTime: String = "",
    val comment: String = "",
)
