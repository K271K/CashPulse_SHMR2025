package com.core.domain.usecase

import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        transactionId: Int,
        transaction: CreateTransactionDomainModel
    ) : Result<Unit> {
        return try {
            repository.updateTransaction(
                transactionId = transactionId,
                transaction = transaction
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}