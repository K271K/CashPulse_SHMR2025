package com.core.data.di

import com.core.data.repository.CategoryRepositoryImpl
import com.core.data.repository.CurrencyRepositoryImpl
import com.core.data.repository.TransactionRepositoryImpl
import com.core.domain.repository.CategoryRepository
import com.core.domain.repository.CurrencyRepository
import com.core.domain.repository.TransactionRepository
import com.core.network.RemoteDataSource
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
    fun provideCurrencyRepository(remoteDataSource: RemoteDataSource): CurrencyRepository {
        return CurrencyRepositoryImpl(
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(remoteDataSource: RemoteDataSource): TransactionRepository {
        return TransactionRepositoryImpl(
            remoteDataSource = remoteDataSource
        )
    }

    @Provides
    fun provideCategoryRepository(
        remoteDataSource: RemoteDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(remoteDataSource = remoteDataSource)
    }

}