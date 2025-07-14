package com.core.data.repository

import com.core.data.models.mappers.toCreateTransactionRequestModel
import com.core.data.models.mappers.toDataModel
import com.core.data.models.mappers.toDomainModel
import com.core.domain.models.CreateTransactionDomainModel
import com.core.domain.models.TransactionDomainModel
import com.core.domain.repository.TransactionRepository
import com.core.network.RemoteDataSource
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
) : TransactionRepository {
    override suspend fun createTransaction(transaction: CreateTransactionDomainModel): CreateTransactionDomainModel {
        val response = remoteDataSource.createTransaction(transaction.toCreateTransactionRequestModel())
        return response.toDomainModel()
    }

    override suspend fun getTransactionById(transactionId: Int): TransactionDomainModel {
        val response = remoteDataSource.getTransactionById(transactionId)
        return response.toDataModel().toDomainModel()
    }

}