package com.feature.account.data.di

import com.core.data.repository.AccountRepositoryImpl
import com.core.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AccountDataModule {

    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

}