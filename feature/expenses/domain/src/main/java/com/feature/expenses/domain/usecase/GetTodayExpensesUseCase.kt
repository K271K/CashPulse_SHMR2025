package com.feature.expenses.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.expenses.domain.repository.ExpensesRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Получить сегодняшние расходы
 */
class GetTodayExpensesUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(): Result<List<TransactionDomainModel>> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = today.format(formatter)
        return expensesRepository.getTodayExpenses(date = dateString, accountId = 211)
    }
}