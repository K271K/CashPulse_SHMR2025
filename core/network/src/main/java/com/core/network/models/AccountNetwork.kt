package com.core.network.models

data class AccountNetwork(
    val balance: String,
    val currency: String,
    val id: Int,
    val name: String,
    val userId: Int?,
    val createdAt: String?,
    val updatedAt: String?
)