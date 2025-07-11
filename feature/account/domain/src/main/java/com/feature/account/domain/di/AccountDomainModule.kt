package com.feature.account.domain.di

import com.feature.account.domain.repository.AccountRepository
import com.feature.account.domain.usecase.GetAccountUseCase
import com.feature.account.domain.usecase.UpdateAccountUseCase
import dagger.Module
import dagger.Provides

@Module
object AccountDomainModule {

    @Provides
    fun provideUpdateAccountUseCase(repository: AccountRepository): UpdateAccountUseCase {
        return UpdateAccountUseCase(repository = repository)
    }

    @Provides
    fun provideGetAccountUseCase(repository: AccountRepository): GetAccountUseCase {
        return GetAccountUseCase(repository = repository)
    }

}