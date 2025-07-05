package com.core.domain.models

/**
 * Domain модель для транзакций.
 * Используется в фичах "Расходы" и "Доходы", поэтому вынесена в :core:domain модуль
 */
data class TransactionDomainModel(
    val account: AccountDomainModel,
    val amount: String,
    val category: CategoryDomainModel,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)

data class CategoryDomainModel(
    val emoji: String,
    val id: Int,
    val isIncome: Boolean,
    val name: String
)

data class AccountDomainModel(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String,
    val userId: Int?,
    val createdAt: String?,
    val updatedAt: String?
)