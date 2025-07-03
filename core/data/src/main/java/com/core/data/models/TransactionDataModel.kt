package com.core.data.models

import com.core.domain.models.AccountDomainModel
import com.core.domain.models.CategoryDomainModel
import com.core.domain.models.TransactionDomainModel
import com.core.network.models.AccountNetwork
import com.core.network.models.CategoryNetwork
import com.core.network.models.TransactionNetwork

/**
 * Здесь я поместил Data модель для транзакций с бэка
 * Также здесь находятся мапперы в Domain модель
 *
 * Domain модель по факту никак не отличается от Data модели,
 * но следуя clean arch я решил сделать явное разделение моделей между слоями
 *
 * Эта модель используется в фичах расходы и доходы, поэтому она именно в модуле :core:data находится
 */
data class TransactionDataModel(
    val account: AccountDataModel,
    val amount: String,
    val category: CategoryDataModel,
    val comment: String,
    val createdAt: String,
    val id: Int,
    val transactionDate: String,
    val updatedAt: String
)

data class CategoryDataModel(
    val emoji: String,
    val id: Int,
    val isIncome: Boolean,
    val name: String
)

data class AccountDataModel(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String
)

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
        name = this.name
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
        name = this.name
    )

/**
 * Я понимаю, что такие вещи возможно стоит выносить в другую папку, но пока решил оставить здесь
 * маппинг списка транзакций из Network в Data модель
 * маппинг списка транзакций из Data в Domain модель
 *
 * P.S. модели никак не отличаются, но специально сделано явное разделение
 */
fun toDomainList(dataModels: List<TransactionDataModel>): List<TransactionDomainModel> =
    dataModels.map { it.toDomainModel() }

fun toDataList(networkModels: List<TransactionNetwork>): List<TransactionDataModel> =
    networkModels.map { it.toDataModel() }