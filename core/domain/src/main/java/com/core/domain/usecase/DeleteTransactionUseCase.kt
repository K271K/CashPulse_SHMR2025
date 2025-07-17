package com.core.domain.usecase

import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Int): Result<Boolean> {
        try {
            repository.deleteTransaction(
                transactionId = transactionId
            )
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}