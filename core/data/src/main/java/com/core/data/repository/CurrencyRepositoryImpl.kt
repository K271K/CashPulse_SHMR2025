package com.core.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.core.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CurrencyRepositoryImpl (
    //private val dataStore: DataStore<Preferences>
): CurrencyRepository {
    companion object {
        val CURRENCY_KEY = stringPreferencesKey("currency")
    }

    override val currency: Flow<String> =
        flowOf("test")
//    dataStore.data.map { preferences ->
//        preferences[CURRENCY_KEY] ?: "RUB"
//    }

    override suspend fun setCurrency(currency: String) {
//        dataStore.edit { preferences ->
//            preferences[CURRENCY_KEY] = currency
//        }
    }
}