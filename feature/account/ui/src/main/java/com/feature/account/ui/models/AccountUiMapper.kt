package com.feature.account.ui.models

import com.core.domain.models.AccountDomainModel
import com.feature.account.ui.screens.AccountScreenData

object AccountUiMapper {

    fun toUiModel(domainModel: AccountDomainModel): AccountScreenData =
        AccountScreenData(
            name = domainModel.name,
            currency = domainModel.currency,
            balance = domainModel.balance
        )
}