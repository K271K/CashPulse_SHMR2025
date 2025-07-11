package com.example.cashpulse.di

import com.core.data.di.CoreDataModule
import com.core.network.di.NetworkModule
import com.example.cashpulse.navigation.DefaultNavigator
import com.feature.account.ui.di.AccountUiModule
import com.feature.account.ui.navigation.AccountNavigation
import com.feature.category.ui.di.CategoryUiModule
import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.expenses.ui.di.ExpensesUiModule
import com.feature.expenses.ui.navigation.ExpensesNavigation
import com.feature.incomes.ui.di.IncomesUiModule
import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.settings.ui.di.SettingsUiModule
import com.feature.settings.ui.navigation.SettingsNavigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ExpensesUiModule::class,
        CategoryUiModule::class,
        AccountUiModule::class,
        SettingsUiModule::class,
        IncomesUiModule::class,
        CoreDataModule::class,
        NetworkModule::class
    ]
)
object AppModule {

    @Provides
    @Singleton
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