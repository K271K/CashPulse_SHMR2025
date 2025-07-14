package com.core.domain.usecase

import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: CreateTransactionDomainModel) : Result<Boolean> {
        println(transaction.transactionDate)
        try {
            repository.createTransaction(transaction = transaction)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}