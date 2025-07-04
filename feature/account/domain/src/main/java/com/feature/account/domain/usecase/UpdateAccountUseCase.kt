package com.feature.account.domain.usecase

import com.core.domain.models.AccountDomainModel
import com.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(account: AccountDomainModel): AccountDomainModel {
        return repository.updateAccount(account = account)
    }
}