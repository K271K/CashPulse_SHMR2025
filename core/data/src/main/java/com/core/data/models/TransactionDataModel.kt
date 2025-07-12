package com.core.data.models

/**
 * Здесь я поместил Data модель для транзакций с бэка
 *
 * Domain модель по факту никак не отличается от Data модели,
 * но следуя clean arch я решил сделать явное разделение моделей между слоями
 *
 * Эта модель используется в фичах расходы и доходы, поэтому она именно в модуле :**core**:data находится
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
