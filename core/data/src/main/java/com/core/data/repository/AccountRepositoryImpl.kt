package com.core.data.repository

import com.core.data.models.mappers.toDataModel
import com.core.data.models.mappers.toDomainModel
import com.core.domain.models.AccountDomainModel
import com.core.domain.repository.AccountRepository
import com.core.network.RemoteDataSource
import com.core.network.models.AccountNetwork
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): AccountRepository {
    override suspend fun getAccount(id: Int): AccountDomainModel {
        val accounts = remoteDataSource.getAccounts()
        val account = accounts.find { it.id == id }
        return account?.toDataModel()?.toDomainModel() ?: throw IllegalStateException("No account found")
    }

    override suspend fun getAccounts(): List<AccountDomainModel> {
        val accounts = remoteDataSource.getAccounts()
        return if (accounts.isNotEmpty()) {
            accounts.map { it.toDataModel().toDomainModel() }
        } else {
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