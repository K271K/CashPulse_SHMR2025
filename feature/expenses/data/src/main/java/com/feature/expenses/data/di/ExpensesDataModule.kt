package com.feature.expenses.data.di

import com.core.network.RemoteDataSource
import com.feature.expenses.data.repository.ExpensesRepositoryImpl
import com.feature.expenses.domain.repository.ExpensesRepository
import dagger.Module
import dagger.Provides

@Module()
object ExpensesDataModule {

    @Provides
    fun provideExpensesRepository(remoteDataSource: RemoteDataSource): ExpensesRepository {
        return ExpensesRepositoryImpl(remoteDataSource = remoteDataSource)
    }


}