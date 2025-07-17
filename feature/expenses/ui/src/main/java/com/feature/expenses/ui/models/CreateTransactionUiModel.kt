package com.feature.expenses.ui.models

data class CreateTransactionUiModel(
    var accountId: Int,
    var categoryId: Int,
    var amount: String,
    var transactionDate: String,
    var transactionTime: String,
    var comment: String
)