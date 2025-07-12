package com.core.domain.usecase

import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: CreateTransactionDomainModel) {
        repository.createTransaction(transaction = transaction)
    }

}