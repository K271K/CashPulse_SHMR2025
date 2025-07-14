package com.feature.account.data.di

import com.feature.account.data.repository.AccountRepositoryImpl
import com.feature.account.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AccountDataModule {

    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

}