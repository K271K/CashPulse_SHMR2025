package com.feature.expenses.ui.screens.expenses_edit

import androidx.compose.runtime.Immutable

sealed class EditExpenseScreenUiState {
    data object Loading: EditExpenseScreenUiState() //Изначальная загрузка
    data object Updating: EditExpenseScreenUiState() //Обновление или удаление
    data class Loaded(val data: EditExpenseScreenData) : EditExpenseScreenUiState() //Загружено
    data class Success(val message: String = "Операция выполнена успешно"): EditExpenseScreenUiState()
    data class Error(val message: String) : EditExpenseScreenUiState()
}

@Immutable
data class EditExpenseScreenData(
    val accountName: String,
    val categoryName: String,
    val amount: String,
    val expenseDate: String,
    val expenseTime: String,
    val comment: String
)