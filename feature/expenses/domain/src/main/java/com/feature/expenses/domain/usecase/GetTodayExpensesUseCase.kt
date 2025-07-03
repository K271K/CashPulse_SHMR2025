package com.feature.expenses.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.expenses.domain.repository.ExpensesRepository

/**
 * Получить сегодняшние расходы
 */
class GetTodayExpensesUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(date: String?): Result<List<TransactionDomainModel>> {
        if (date?.isBlank() == true){
            return Result.failure(Exception("Date is blank"))
        }
        return expensesRepository.getTodayExpenses(date = date)
    }
}