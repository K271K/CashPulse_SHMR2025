package com.feature.expenses.domain.repository

import com.core.domain.models.TransactionDomainModel

interface ExpensesRepository {

    suspend fun getTodayExpenses(date: String?, accountId: Int) : Result<List<TransactionDomainModel>>

    suspend fun getExpensesForPeriod(startDate: String?, endDate: String?, accountId: Int) : Result<List<TransactionDomainModel>>
}