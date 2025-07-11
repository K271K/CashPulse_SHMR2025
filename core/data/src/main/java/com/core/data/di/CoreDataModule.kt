package com.core.data.di

import com.core.data.repository.CurrencyRepositoryImpl
import com.core.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object CoreDataModule {

//    @Provides
//    @Singleton
//    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create {
//            context.preferencesDataStoreFile("currency_prefs")
//        }
//    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(): CurrencyRepository {
        return CurrencyRepositoryImpl()
    }

}