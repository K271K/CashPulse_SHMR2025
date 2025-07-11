package com.feature.incomes.data.di

import com.core.network.RemoteDataSource
import com.feature.incomes.data.repository.IncomesRepositoryImpl
import com.feature.incomes.domain.repository.IncomesRepository
import dagger.Module
import dagger.Provides

@Module()
object IncomesDataModule {

    @Provides
    fun provideIncomesRepository(remoteDataSource: RemoteDataSource): IncomesRepository {
        return IncomesRepositoryImpl(remoteDataSource = remoteDataSource)
    }


}