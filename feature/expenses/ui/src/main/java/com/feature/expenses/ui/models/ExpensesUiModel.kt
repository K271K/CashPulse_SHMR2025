package com.feature.expenses.ui.models

data class ExpensesUiModel(
    val id: Int,
    val icon: String,
    val category: String,
    val comment: String,
    val amount: String
)
