package com.core.domain.repository

import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.models.TransactionDomainModel

interface TransactionRepository {

    suspend fun createTransaction(transaction: CreateTransactionDomainModel) : CreateTransactionDomainModel

    suspend fun getTransactionById(transactionId: Int): TransactionDomainModel

    suspend fun deleteTransaction(transactionId: Int)

    suspend fun updateTransaction(transaction: CreateTransactionDomainModel, transactionId: Int)
}