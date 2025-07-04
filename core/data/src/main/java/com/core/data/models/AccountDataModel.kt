package com.core.data.models

data class AccountDataModel(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String,
    val userId: Int?,
    val createdAt: String?,
    val updatedAt: String?
)