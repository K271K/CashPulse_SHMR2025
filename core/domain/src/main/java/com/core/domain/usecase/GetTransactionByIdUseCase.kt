package com.core.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionId: Int): Result<TransactionDomainModel> {
        return try {
            val transaction  = repository.getTransactionById(
                transactionId = transactionId
            )
            return Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}