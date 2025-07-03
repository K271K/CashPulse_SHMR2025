package com.feature.account.ui.di

import com.feature.account.ui.navigation.AccountNavigation
import com.feature.account.ui.navigation.AccountNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AccountUiModule {
    @Provides
    fun provideIncomesNavigation(): AccountNavigation{
        return AccountNavigationImpl()
    }
}