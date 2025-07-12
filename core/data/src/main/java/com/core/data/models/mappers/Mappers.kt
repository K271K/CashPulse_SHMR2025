package com.core.data.models.mappers

import com.core.data.models.AccountDataModel
import com.core.data.models.CategoryDataModel
import com.core.data.models.TransactionDataModel
import com.core.domain.models.AccountDomainModel
import com.core.domain.models.CategoryDomainModel
import com.core.domain.models.TransactionDomainModel
import com.core.network.models.AccountNetwork
import com.core.network.models.CategoryNetwork
import com.core.network.models.CreateTransactionRequestModel
import com.core.network.models.TransactionNetwork

fun TransactionDataModel.toDomainModel() =
    TransactionDomainModel(
        account = this.account.toDomainModel(),
        amount = this.amount,
        category = this.category.toDomainModel(),
        comment = this.comment,
        createdAt = this.createdAt,
        id = this.id,
        transactionDate = this.transactionDate,
        updatedAt = this.updatedAt
    )

fun CategoryDataModel.toDomainModel()=
    CategoryDomainModel(
        emoji = this.emoji,
        id = this.id,
        isIncome = this.isIncome,
        name = this.name
    )

fun AccountDataModel.toDomainModel()=
    AccountDomainModel(
        balance = this.balance,
        currency = this.currency,
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

fun TransactionNetwork.toDataModel() =
    TransactionDataModel(
        account = this.account.toDataModel(),
        amount = this.amount,
        category = this.category.toDataModel(),
        comment = this.comment,
        createdAt = this.createdAt,
        id = this.id,
        transactionDate = this.transactionDate,
        updatedAt = this.updatedAt
    )

fun CategoryNetwork.toDataModel()=
    CategoryDataModel(
        emoji = this.emoji,
        id = this.id,
        isIncome = this.isIncome,
        name = this.name
    )

fun AccountNetwork.toDataModel()=
    AccountDataModel(
        balance = this.balance,
        currency = this.currency,
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun TransactionDomainModel.toCreateTransactionRequestModel() =
    CreateTransactionRequestModel(
        accountId = this.account.id,
        categoryId = this.category.id,
        amount = this.amount,
        transactionDate = this.transactionDate,
        comment = this.comment
    )

/**
 * маппинг списка транзакций из Network в Data модель
 *
 * маппинг списка транзакций из Data в Domain модель
 *
 * P.S. модели никак не отличаются, но специально сделано явное разделение
 */
fun toDomainList(dataModels: List<TransactionDataModel>): List<TransactionDomainModel> =
    dataModels.map { it.toDomainModel() }

fun toDataList(networkModels: List<TransactionNetwork>): List<TransactionDataModel> =
    networkModels.map { it.toDataModel() }