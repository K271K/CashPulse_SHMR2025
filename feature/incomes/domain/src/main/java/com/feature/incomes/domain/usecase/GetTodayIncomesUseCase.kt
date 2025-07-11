package com.feature.incomes.domain.usecase

import com.core.domain.models.TransactionDomainModel
import com.feature.incomes.domain.repository.IncomesRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetTodayIncomesUseCase(
    private val incomesRepository: IncomesRepository
) {
    suspend operator fun invoke(): Result<List<TransactionDomainModel>> {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateString = today.format(formatter)
        return incomesRepository.getTodayIncomes(date = dateString, accountId = 211)
    }
}