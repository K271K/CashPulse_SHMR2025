package com.core.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionId: Int): TransactionDomainModel {
        return repository.getTransactionById(
            transactionId = transactionId
        )
    }
}