package com.feature.settings.ui.di

import com.feature.settings.ui.navigation.SettingsNavigation
import com.feature.settings.ui.navigation.SettingsNavigationImpl
import dagger.Module
import dagger.Provides


@Module
object SettingsUiModule {
    @Provides
    fun provideIncomesNavigation(): SettingsNavigation{
        return SettingsNavigationImpl()
    }
}