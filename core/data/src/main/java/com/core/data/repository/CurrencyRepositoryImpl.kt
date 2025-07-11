package com.core.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.core.domain.repository.CurrencyRepository
import com.core.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CurrencyRepositoryImpl (
    private val remoteDataSource: RemoteDataSource,
): CurrencyRepository {

    override suspend fun getCurrency(): String {
        val accounts = remoteDataSource.getAccounts()
        val account = accounts.find { it.id == 211 }
        val currency = account?.currency ?: "USD"
        return when (currency) {
            "USD" -> "$"
            "EUR" -> "€"
            "RUB" -> "₽"
            else -> "₽"
        }
    }

}