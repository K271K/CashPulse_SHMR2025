package com.feature.incomes.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.incomes.domain.repository.IncomesRepository

class GetTodayIncomesUseCase(
    private val incomesRepository: IncomesRepository
) {
    suspend operator fun invoke(date: String?): Result<List<TransactionDomainModel>> {
        if (date?.isBlank() == true) {
            return Result.failure(Exception("Date is blank"))
        }
        return incomesRepository.getTodayIncomes(date = date)
    }
}