package com.feature.expenses.data.repository

import com.core.data.models.mappers.toDataList
import com.core.data.models.mappers.toDomainList
import com.core.domain.models.TransactionDomainModel
import com.core.network.RemoteDataSource
import com.feature.expenses.domain.repository.ExpensesRepository
import javax.inject.Inject

/**
 * Репозиторий который отвечает за работу с расходами
 */
class ExpensesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ExpensesRepository {
    override suspend fun getTodayExpenses(date: String?): Result<List<TransactionDomainModel>> {
        try {
            val result = remoteDataSource.getAccountTransactionsForPeriod(
                accountId = 211,
                startDate = date,
                endDate = date
            )
            val dataResult = toDataList(result)
            val domainResult = toDomainList(dataResult).filter {
                !it.category.isIncome
            }
            return Result.success(domainResult)
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }

    override suspend fun getExpensesForPeriod(
        startDate: String?,
        endDate: String?
    ): Result<List<TransactionDomainModel>> {
        try {
            val result = remoteDataSource.getAccountTransactionsForPeriod(
                accountId = 211,
                startDate = startDate,
                endDate = endDate
            )
            val dataResult = toDataList(result)
            val domainResult = toDomainList(dataResult).filter { !it.category.isIncome }.sortedBy { it.transactionDate }
            return Result.success(domainResult)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}