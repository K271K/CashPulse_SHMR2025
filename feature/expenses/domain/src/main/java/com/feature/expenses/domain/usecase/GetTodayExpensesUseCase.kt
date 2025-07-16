package com.feature.expenses.domain.usecase

import com.core.domain.constants.CoreDomainConstants.ACCOUNT_ID
import com.core.domain.models.TransactionDomainModel
import com.feature.expenses.domain.repository.ExpensesRepository
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Получить сегодняшние расходы
 */
class GetTodayExpensesUseCase(
    private val expensesRepository: ExpensesRepository
) {
    suspend operator fun invoke(): Result<List<TransactionDomainModel>> {
        val todayUtc = LocalDate.now(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = todayUtc.format(formatter)
        return expensesRepository.getTodayExpenses(date = dateString, accountId = ACCOUNT_ID)
    }
}