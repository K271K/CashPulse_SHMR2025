package com.feature.expenses.domain.repository

import com.core.domain.models.TransactionDomainModel

interface ExpensesRepository {

    suspend fun getTodayExpenses(date: String?) : Result<List<TransactionDomainModel>>

    suspend fun getExpensesForPeriod(startDate: String?, endDate: String?) : Result<List<TransactionDomainModel>>
}