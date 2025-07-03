package com.feature.incomes.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.incomes.domain.repository.IncomesRepository
import javax.inject.Inject

class GetIncomesForPeriodUseCase @Inject constructor(
    private val incomesRepository: IncomesRepository
) {
    suspend operator fun invoke(
        startDate: String?,
        endDate: String?
    ): Result<List<TransactionDomainModel>> {
        return incomesRepository.getIncomesForPeriod(
            startDate = startDate,
            endDate = endDate
        )
    }
}