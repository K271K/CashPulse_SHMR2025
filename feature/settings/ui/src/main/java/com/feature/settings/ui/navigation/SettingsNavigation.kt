package com.feature.settings.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.settings.ui.screens.SettingsScreen

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface SettingsNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 */
internal class SettingsNavigationImpl : SettingsNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Settings>(
            startDestination = Dest.Settings
        ) {
            composable<Dest.Settings> {
                SettingsScreen()
            }
        }
    }

}