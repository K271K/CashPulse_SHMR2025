package com.feature.account.data.repository

import com.core.data.models.mappers.toDataModel
import com.core.data.models.mappers.toDomainModel
import com.core.domain.models.AccountDomainModel
import com.core.network.RemoteDataSource
import com.core.network.models.AccountNetwork
import com.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): AccountRepository {
    override suspend fun getAccount(): AccountDomainModel {
        val accounts = remoteDataSource.getAccounts()
        return if (accounts.isNotEmpty()){
            accounts[0].toDataModel().toDomainModel()
        } else{
            throw IllegalStateException("No accounts found")
        }
    }

    override suspend fun updateAccount(account: AccountDomainModel): AccountDomainModel {
        val updatedAccount = remoteDataSource.updateAccount(
            id = account.id,
            account = AccountNetwork(
                balance = account.balance,
                currency = account.currency,
                id = account.id,
                name = account.name,
                userId = account.userId,
                createdAt = account.createdAt,
                updatedAt = account.updatedAt
            )
        )
        return updatedAccount.toDataModel().toDomainModel()
    }
}