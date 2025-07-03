package com.feature.incomes.domain.di

import com.feature.incomes.domain.repository.IncomesRepository
import com.feature.incomes.domain.usecase.GetTodayIncomesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object IncomesDomainModule {

    @Provides
    fun provideGetTodayIncomesUseCase(incomesRepository: IncomesRepository):GetTodayIncomesUseCase {
        return GetTodayIncomesUseCase(incomesRepository = incomesRepository)
    }
}