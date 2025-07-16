package com.feature.expenses.ui.di

import com.core.data.di.CoreDataModule
import com.feature.expenses.data.di.ExpensesDataModule
import com.feature.expenses.domain.di.ExpensesDomainModule
import com.feature.expenses.ui.navigation.ExpensesNavigation
import com.feature.expenses.ui.navigation.ExpensesNavigationImpl
import com.feature.expenses.ui.screens.expenses_add.AddExpenseScreenViewModel
import com.feature.expenses.ui.screens.expenses_add.AddExpenseViewModelFactory
import com.feature.expenses.ui.screens.expenses_edit.EditExpenseScreenViewModel
import com.feature.expenses.ui.screens.expenses_edit.EditExpenseViewModelFactory
import com.feature.expenses.ui.screens.expenses_history.ExpensesHistoryViewModel
import com.feature.expenses.ui.screens.expenses_history.ExpensesHistoryViewModelFactory
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayViewModel
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        ExpensesDomainModule::class,
        ExpensesDataModule::class,
        CoreDataModule::class,
    ]
)
object ExpensesUiModule {
    @Provides
    @Singleton
    fun provideExpensesNavigation(
        expensesTodayViewModelFactory: ExpensesTodayViewModelFactory,
        expensesHistoryViewModelFactory: ExpensesHistoryViewModelFactory,
        addExpenseViewModelFactory: AddExpenseViewModelFactory,
        editExpenseViewModelFactory: EditExpenseViewModelFactory
        ): ExpensesNavigation {
        return ExpensesNavigationImpl(
            expensesTodayViewModelFactory = expensesTodayViewModelFactory,
            expensesHistoryViewModelFactory = expensesHistoryViewModelFactory,
            addExpenseViewModelFactory = addExpenseViewModelFactory,
            editExpenseViewModelFactory = editExpenseViewModelFactory
        )
    }

    @Provides
    @Singleton
    fun provideExpensesTodayViewModelFactory(
        viewModelProvider: Provider<ExpensesTodayViewModel>
    ) : ExpensesTodayViewModelFactory {
        return ExpensesTodayViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideExpensesHistoryViewModelFactory(
        viewModelProvider: Provider<ExpensesHistoryViewModel>
    ) : ExpensesHistoryViewModelFactory {
        return ExpensesHistoryViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideAddExpenseViewModelFactory(
        viewModelProvider: Provider<AddExpenseScreenViewModel>
    ) : AddExpenseViewModelFactory {
        return AddExpenseViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideEditExpenseViewModelFactory(
        viewModelProvider: Provider<EditExpenseScreenViewModel>
    ) : EditExpenseViewModelFactory {
        return EditExpenseViewModelFactory(viewModelProvider)
    }
}

