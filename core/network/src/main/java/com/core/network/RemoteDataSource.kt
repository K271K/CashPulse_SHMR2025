package com.core.network

import com.core.network.models.TransactionNetwork

/**
 * Абстракция над сетевыми запросами
 */
interface RemoteDataSource {

    suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionNetwork>

}