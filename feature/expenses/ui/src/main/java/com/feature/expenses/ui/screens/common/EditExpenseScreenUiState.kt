package com.feature.expenses.ui.screens.common

import com.core.ui.models.CategoryPickerUiModel

enum class TransactionCreationState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}

/**
 * UiState для экранов добавления расходов и редактирования расходов
 */
data class EditExpenseScreenUiState(
    val categories: List<CategoryPickerUiModel> = emptyList(),
    val selectedCategory: CategoryPickerUiModel? = null,
    val amount: String = "",
    val date: String = "",
    val time: String = "",
    val comment: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val transactionCreationState: TransactionCreationState = TransactionCreationState.IDLE
) {
    val isFormValid: Boolean
        get() = selectedCategory != null &&
                amount.isNotBlank() &&
                date.isNotBlank() &&
                time.isNotBlank()

    fun getValidationErrors(): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (selectedCategory == null) {
            errors["category"] = "Выберите категорию"
        }
        if (amount.isBlank()) {
            errors["amount"] = "Введите сумму"
        }
        if (date.isBlank()) {
            errors["date"] = "Выберите дату"
        }
        if (time.isBlank()) {
            errors["time"] = "Выберите время"
        }

        return errors
    }
}
