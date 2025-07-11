package com.core.domain.di

import com.core.domain.repository.CurrencyRepository
import com.core.domain.usecase.GetCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
object CoreDomainModule {

    @Provides
    fun provideGetCurrencyUseCase(repository: CurrencyRepository): GetCurrencyUseCase {
        return GetCurrencyUseCase(repository)
    }

}