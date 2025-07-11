package com.feature.account.domain.usecase

import com.core.domain.models.AccountDomainModel
import com.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: AccountDomainModel): Result<AccountDomainModel> {
        try {
            val result = repository.updateAccount(account = account)
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }
}