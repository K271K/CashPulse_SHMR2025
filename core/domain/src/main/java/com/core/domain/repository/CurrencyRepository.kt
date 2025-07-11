package com.core.domain.repository

interface CurrencyRepository {
    suspend fun getCurrency(): String
}