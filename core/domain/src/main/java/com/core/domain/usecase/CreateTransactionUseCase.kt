package com.core.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: TransactionDomainModel) {
        repository.createTransaction(transaction = transaction)
    }

}