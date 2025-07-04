package com.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    val currency: Flow<String>
    suspend fun setCurrency(currency: String)
}