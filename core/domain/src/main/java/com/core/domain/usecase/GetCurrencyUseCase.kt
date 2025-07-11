package com.core.domain.usecase

import com.core.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(): Result<String> {
        return try {
            Result.success(repository.getCurrency())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
