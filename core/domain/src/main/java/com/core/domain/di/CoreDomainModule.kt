package com.core.domain.di

import com.core.domain.repository.CurrencyRepository
import com.core.domain.usecase.GetCurrencyUseCase
import com.core.domain.usecase.SetCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CoreDomainModule {

    @Provides
    fun provideGetCurrencyUseCase(repository: CurrencyRepository): GetCurrencyUseCase {
        return GetCurrencyUseCase(repository)
    }

    @Provides
    fun provideSetCurrencyUseCase(repository: CurrencyRepository): SetCurrencyUseCase {
        return SetCurrencyUseCase(repository)
    }

}