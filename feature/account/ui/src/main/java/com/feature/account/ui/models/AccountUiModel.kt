package com.feature.account.ui.models

import com.core.domain.models.AccountDomainModel

data class AccountUiModel(
    val id: Int,
    val name: String,
    val currency: String,
    val balance: String
)
