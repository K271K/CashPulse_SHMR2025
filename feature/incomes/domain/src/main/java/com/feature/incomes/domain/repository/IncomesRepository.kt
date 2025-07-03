package com.feature.incomes.domain.repository

import com.core.domain.models.TransactionDomainModel

interface IncomesRepository {

    suspend fun getTodayIncomes(date: String?): Result<List<TransactionDomainModel>>

    suspend fun getIncomesForPeriod(
        startDate: String?,
        endDate: String?
    ): Result<List<TransactionDomainModel>>
}