package com.feature.account.ui.screens.accounts_screen

import androidx.compose.runtime.Immutable
import com.core.domain.models.AccountDomainModel

@Immutable
sealed interface AccountScreenState {
    data object Loading: AccountScreenState
    data class Loaded(val data: AccountScreenData): AccountScreenState
    data class Error(val message: String): AccountScreenState
}

@Immutable
data class AccountScreenItem(
    val id: Int,
    val name: String,
    val currency: String,
    val balance: String,
    val isSelected: Boolean
) {
    fun toDomainAccount(id: Int, userId: Int? = 12, createdAt: String? = null, updatedAt: String? = null): AccountDomainModel =
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

@Immutable
data class AccountScreenData(
    val accounts: List<AccountScreenItem>
)
