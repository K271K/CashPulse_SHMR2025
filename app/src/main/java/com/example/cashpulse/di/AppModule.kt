package com.example.cashpulse.di

import com.example.cashpulse.navigation.DefaultNavigator
import com.feature.account.ui.navigation.AccountNavigation
import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.expenses.ui.navigation.ExpensesNavigation
import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.settings.ui.navigation.SettingsNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module()
object AppModule {

    @Provides
    fun provideDefaultNavigator(
        expenses: ExpensesNavigation,
        incomes: IncomesNavigation,
        account: AccountNavigation,
        category: CategoryNavigation,
        settings: SettingsNavigation
    ) : DefaultNavigator {
        return DefaultNavigator(
            expenses = expenses,
            incomes = incomes,
            account = account,
            category = category,
            settings = settings
        )
    }
}