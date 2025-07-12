package com.core.data.models

data class CreateTransactionResponseDomainModel(
    var id: Int? = null,
    var accountId: Int? = null,
    var categoryId: Int? = null,
    var amount: String? = null,
    var transactionDate: String? = null,
    var comment: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
)
