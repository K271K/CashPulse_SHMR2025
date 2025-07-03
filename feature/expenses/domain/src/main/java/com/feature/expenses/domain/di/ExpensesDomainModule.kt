package com.feature.expenses.domain.di

import com.feature.expenses.domain.repository.ExpensesRepository
import com.feature.expenses.domain.usecase.GetExpensesForPeriodUseCase
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object ExpensesDomainModule {

    @Provides
    fun provideGetTodayExpensesUseCase(expensesRepository: ExpensesRepository): GetTodayExpensesUseCase {
        return GetTodayExpensesUseCase(expensesRepository = expensesRepository)
    }

    @Provides
    fun provideGetExpensesForPeriodUseCase(expensesRepository: ExpensesRepository): GetExpensesForPeriodUseCase {
        return GetExpensesForPeriodUseCase(expensesRepository = expensesRepository)
    }
}