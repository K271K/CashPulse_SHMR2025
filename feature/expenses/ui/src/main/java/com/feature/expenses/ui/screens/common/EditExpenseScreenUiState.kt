package com.feature.expenses.ui.screens.common

import com.core.ui.models.CategoryPickerUiModel

/**
 * Общий UiState для экранов добавления и редактирования расхода.
 */
data class EditExpenseScreenUiState(
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
) {
    fun getValidationErrors(): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (selectedCategory == null) {
            errors["category"] = "Выберите категорию"
        }
        if (amount.isBlank()) {
            errors["amount"] = "Введите сумму"
        }
        if (expenseDate.isBlank()) {
            errors["date"] = "Выберите дату"
        }
        if (expenseTime.isBlank()) {
            errors["time"] = "Выберите время"
        }

        return errors
    }
}