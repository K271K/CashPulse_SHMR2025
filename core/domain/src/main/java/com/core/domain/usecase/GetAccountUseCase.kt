package com.core.domain.usecase

import com.core.domain.models.AccountDomainModel
import com.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(id: Int): Result<AccountDomainModel> {
        return try {
            val result = repository.getAccount(id)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}