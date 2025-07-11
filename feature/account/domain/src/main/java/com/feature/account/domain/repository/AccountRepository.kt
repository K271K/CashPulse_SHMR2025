package com.feature.account.domain.repository

import com.core.domain.models.AccountDomainModel

interface AccountRepository {
    suspend fun getAccount(id: Int): AccountDomainModel

    suspend fun getAccounts(): List<AccountDomainModel>
    suspend fun updateAccount(account: AccountDomainModel): AccountDomainModel
}