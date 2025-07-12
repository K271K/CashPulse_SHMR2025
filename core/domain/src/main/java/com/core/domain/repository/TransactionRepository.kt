package com.core.domain.repository

import com.core.domain.models.CreateTransactionDomainModel

interface TransactionRepository {

    suspend fun createTransaction(transaction: CreateTransactionDomainModel) : CreateTransactionDomainModel

}