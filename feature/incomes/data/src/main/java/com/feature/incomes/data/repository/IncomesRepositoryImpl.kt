package com.feature.incomes.data.repository

import com.core.data.models.mappers.toDataList
import com.core.data.models.mappers.toDomainList
import com.core.domain.models.TransactionDomainModel
import com.core.network.RemoteDataSource
import com.feature.incomes.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): IncomesRepository {
    override suspend fun getTodayIncomes(date: String?, accountId: Int): Result<List<TransactionDomainModel>> {
        try {
            val result = remoteDataSource.getAccountTransactionsForPeriod(
                accountId = accountId,
                startDate = date,
                endDate = date
            )
            val dataResult = toDataList(result)
            val domainResult = toDomainList(dataResult).filter {
                it.category.isIncome
            }
            return Result.success(domainResult)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getIncomesForPeriod(
        startDate: String?,
        endDate: String?,
        accountId: Int
    ): Result<List<TransactionDomainModel>> {
        try {
            val result = remoteDataSource.getAccountTransactionsForPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )
            val dataResult = toDataList(result)
            val domainResult = toDomainList(dataResult).filter { it.category.isIncome }.sortedBy { it.transactionDate }
            return Result.success(domainResult)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}