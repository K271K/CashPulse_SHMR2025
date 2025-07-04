package com.feature.account.data.di

import com.core.network.RemoteDataSource
import com.feature.account.data.repository.AccountRepositoryImpl
import com.feature.account.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AccountDataModule {

    @Provides
    fun provideAccountRepository(
        remoteDataSource: RemoteDataSource
    ): AccountRepository = AccountRepositoryImpl(
        remoteDataSource = remoteDataSource
    )

}