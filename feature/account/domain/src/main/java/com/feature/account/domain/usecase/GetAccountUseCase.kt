package com.feature.account.domain.usecase

import com.core.domain.models.AccountDomainModel
import com.feature.account.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(id: Int): AccountDomainModel {
        return repository.getAccount(id)
    }
}