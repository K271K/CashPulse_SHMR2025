package com.feature.expenses.data.di

import com.core.network.RemoteDataSource
import com.feature.expenses.data.repository.ExpensesRepositoryImpl
import com.feature.expenses.domain.repository.ExpensesRepository
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module()
object ExpensesDataModule {

    @Provides
    fun provideExpensesRepository(remoteDataSource: RemoteDataSource): ExpensesRepository {
        return ExpensesRepositoryImpl(remoteDataSource = remoteDataSource)
    }


}