package com.feature.expenses.ui.di

import com.feature.expenses.ui.navigation.ExpensesNavigation
import com.feature.expenses.ui.navigation.ExpensesNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module()
object ExpensesUiModule {
    @Provides
    fun provideExpensesNavigation(): ExpensesNavigation {
        return ExpensesNavigationImpl()
    }

}