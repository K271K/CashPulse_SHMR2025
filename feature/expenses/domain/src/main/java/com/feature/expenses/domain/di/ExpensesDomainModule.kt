package com.feature.expenses.domain.di

import com.feature.expenses.domain.repository.ExpensesRepository
import com.feature.expenses.domain.usecase.GetExpensesForPeriodUseCase
import com.feature.expenses.domain.usecase.GetTodayExpensesUseCase
import dagger.Module
import dagger.Provides


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