package com.feature.account.ui.screens

import androidx.compose.runtime.Immutable
import com.core.domain.models.AccountDomainModel

@Immutable
sealed interface AccountScreenState {
    data object Loading: AccountScreenState
    data class Loaded(val data: AccountScreenData): AccountScreenState
    data class Error(val message: String): AccountScreenState
}

@Immutable
data class AccountScreenData(
    val name: String,
    val currency: String,
    val balance: String
) {
    fun toDomainAccount(id: Int = 211, userId: Int? = 12, createdAt: String? = null, updatedAt: String? = null): AccountDomainModel =
        AccountDomainModel(
            balance = balance,
            currency = currency,
            id = id,
            name = name,
            userId = userId,
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: ""
        )
}

