package com.core.domain.repository

import com.core.domain.models.TransactionDomainModel

interface TransactionRepository {

    suspend fun createTransaction(transaction: TransactionDomainModel) : TransactionDomainModel

}