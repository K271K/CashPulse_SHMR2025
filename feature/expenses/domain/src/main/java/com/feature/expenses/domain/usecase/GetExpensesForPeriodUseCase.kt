package com.feature.expenses.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.expenses.domain.repository.ExpensesRepository

/**
 * Получить расходы за период
 */
class GetExpensesForPeriodUseCase (
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(startDate: String?, endDate: String?, accountId: Int): Result<List<TransactionDomainModel>> {
        return expensesRepository.getExpensesForPeriod(startDate, endDate, accountId)
    }

}