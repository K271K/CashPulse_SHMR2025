package com.core.data.repository

import com.core.domain.models.TransactionDomainModel
import com.core.domain.repository.TransactionRepository
import com.core.network.RemoteDataSource
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor (
    private val remoteDataSource: RemoteDataSource,
) : TransactionRepository {
    override suspend fun createTransaction(transaction: TransactionDomainModel): TransactionDomainModel {
        remoteDataSource.createTransaction(transaction)
    }

}