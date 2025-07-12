package com.core.network.models

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponseModel(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("accountId")
    var accountId: Int? = null,
    @SerializedName("categoryId")
    var categoryId: Int? = null,
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("transactionDate")
    var transactionDate: String? = null,
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @SerializedName("updatedAt")
    var updatedAt: String? = null
)
