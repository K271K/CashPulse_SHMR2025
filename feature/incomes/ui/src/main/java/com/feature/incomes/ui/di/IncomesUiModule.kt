package com.feature.incomes.ui.di

import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.incomes.ui.navigation.IncomesNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object IncomesUiModule {

    @Provides
    fun provideIncomesNavigation(): IncomesNavigation{
        return IncomesNavigationImpl()
    }
}