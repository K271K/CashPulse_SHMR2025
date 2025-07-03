package com.core.network.models

data class TransactionNetwork(
    val account: AccountNetwork,
    val amount: String,
    val category: CategoryNetwork,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)



