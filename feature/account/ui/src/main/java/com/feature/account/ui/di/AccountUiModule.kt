package com.feature.account.ui.di

import com.feature.account.data.di.AccountDataModule
import com.feature.account.domain.di.AccountDomainModule
import com.feature.account.ui.navigation.AccountNavigation
import com.feature.account.ui.navigation.AccountNavigationImpl
import com.feature.account.ui.screens.account_edit_screen.AccountEditViewModel
import com.feature.account.ui.screens.account_edit_screen.AccountEditViewModelFactory
import com.feature.account.ui.screens.accounts_screen.AccountViewModelFactory
import com.feature.account.ui.screens.accounts_screen.AccountsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        AccountDomainModule::class,
        AccountDataModule::class,
    ]
)
object AccountUiModule {
    @Provides
    @Singleton
    fun provideIncomesNavigation(
        accountsViewModelFactory: AccountViewModelFactory,
        accountEditViewModelFactory: AccountEditViewModelFactory
    ): AccountNavigation{
        return AccountNavigationImpl(
            accountsViewModelFactory = accountsViewModelFactory,
            accountEditViewModelFactory = accountEditViewModelFactory
        )
    }

    @Provides
    @Singleton
    fun provideAccountViewModelFactory(
        viewModelProvider: Provider<AccountsViewModel>
    ) : AccountViewModelFactory {
        return AccountViewModelFactory(viewModelProvider)
    }

    @Provides
    @Singleton
    fun provideAccountEditViewModelFactory(
        viewModelProvider: Provider<AccountEditViewModel>
    ) : AccountEditViewModelFactory {
        return AccountEditViewModelFactory(viewModelProvider)
    }

}