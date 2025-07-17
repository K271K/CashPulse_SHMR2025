package com.feature.account.domain.usecase

import com.core.domain.models.AccountDomainModel
import com.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke() : Result<List<AccountDomainModel>> {
        try {
            val accountsList = repository.getAccounts()
            return Result.success(accountsList)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}