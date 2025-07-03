package com.feature.settings.ui.di

import com.feature.settings.ui.navigation.SettingsNavigation
import com.feature.settings.ui.navigation.SettingsNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SettingsUiModule {
    @Provides
    fun provideIncomesNavigation(): SettingsNavigation{
        return SettingsNavigationImpl()
    }
}