package com.feature.incomes.ui.di

import com.core.data.di.CoreDataModule
import com.feature.incomes.data.di.IncomesDataModule
import com.feature.incomes.domain.di.IncomesDomainModule
import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.incomes.ui.navigation.IncomesNavigationImpl
import com.feature.incomes.ui.screens.incomes_history.IncomesHistoryViewModel
import com.feature.incomes.ui.screens.incomes_history.IncomesHistoryViewModelFactory
import com.feature.incomes.ui.screens.incomes_today.IncomesTodayViewModel
import com.feature.incomes.ui.screens.incomes_today.IncomesTodayViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        IncomesDomainModule::class,
        IncomesDataModule::class,
        CoreDataModule::class
    ]
)
object IncomesUiModule {

    @Provides
    fun provideIncomesNavigation(
        incomesTodayViewModelFactory: IncomesTodayViewModelFactory,
        incomesHistoryViewModelFactory: IncomesHistoryViewModelFactory
    ): IncomesNavigation{
        return IncomesNavigationImpl(
            incomesTodayViewModelFactory = incomesTodayViewModelFactory,
            incomesHistoryViewModelFactory = incomesHistoryViewModelFactory
        )
    }

    @Provides
    @Singleton
    fun provideIncomeTodayViewModelFactory(
        viewModelProvider: Provider<IncomesTodayViewModel>
    ) : IncomesTodayViewModelFactory {
        return IncomesTodayViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideIncomesHistoryViewModelFactory(
        viewModelProvider: Provider<IncomesHistoryViewModel>
    ) : IncomesHistoryViewModelFactory {
        return IncomesHistoryViewModelFactory(viewModelProvider)
    }

}