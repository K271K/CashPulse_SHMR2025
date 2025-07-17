package com.feature.incomes.ui.di

import com.core.data.di.CoreDataModule
import com.feature.incomes.data.di.IncomesDataModule
import com.feature.incomes.domain.di.IncomesDomainModule
import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.incomes.ui.navigation.IncomesNavigationImpl
import com.feature.incomes.ui.screens.incomes_add.IncomesAddScreenViewModel
import com.feature.incomes.ui.screens.incomes_add.IncomesAddScreenViewModelFactory
import com.feature.incomes.ui.screens.incomes_edit.IncomesEditScreenViewModel
import com.feature.incomes.ui.screens.incomes_edit.IncomesEditScreenViewModelFactory
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
        incomesHistoryViewModelFactory: IncomesHistoryViewModelFactory,
        incomesAddViewModelFactory: IncomesAddScreenViewModelFactory,
        incomesEditViewModelFactory: IncomesEditScreenViewModelFactory
    ): IncomesNavigation{
        return IncomesNavigationImpl(
            incomesTodayViewModelFactory = incomesTodayViewModelFactory,
            incomesHistoryViewModelFactory = incomesHistoryViewModelFactory,
            incomesAddViewModelFactory = incomesAddViewModelFactory,
            incomesEditViewModelFactory = incomesEditViewModelFactory
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

    @Provides
    @Singleton
    fun provideIncomesAddViewModelFactory(
        viewModelProvider: Provider<IncomesAddScreenViewModel>
    ) : IncomesAddScreenViewModelFactory {
        return IncomesAddScreenViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideIncomesEditViewModelFactory(
        viewModelProvider: Provider<IncomesEditScreenViewModel>
    ) : IncomesEditScreenViewModelFactory {
        return IncomesEditScreenViewModelFactory(viewModelProvider)
    }

}