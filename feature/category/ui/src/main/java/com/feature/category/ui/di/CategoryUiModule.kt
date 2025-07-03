package com.feature.category.ui.di

import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.category.ui.navigation.CategoryNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CategoryUiModule {
    @Provides
    fun provideIncomesNavigation(): CategoryNavigation{
        return CategoryNavigationImpl()
    }
}