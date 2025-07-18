package com.core.network

import com.core.network.models.AccountNetwork
import com.core.network.models.CategoryNetwork
import com.core.network.models.CreateTransactionRequestModel
import com.core.network.models.CreateTransactionResponseModel
import com.core.network.models.TransactionNetwork
import okhttp3.Response

/**
 * Абстракция над сетевыми запросами
 */
interface RemoteDataSource {

    suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionNetwork>

    suspend fun getCategories(): List<CategoryNetwork>

    suspend fun getAccounts(): List<AccountNetwork>

    suspend fun updateAccount(
        id: Int,
        account: AccountNetwork
    ): AccountNetwork

    suspend fun createTransaction(
        transaction: CreateTransactionRequestModel
    ) : CreateTransactionResponseModel

    suspend fun updateTransaction(
        transaction: CreateTransactionRequestModel,
        transactionId: Int
    ) : CreateTransactionResponseModel

    suspend fun getTransactionById(
        transactionId:Int
    ) : TransactionNetwork

    suspend fun deleteTransaction(
        transactionId: Int
    )

}