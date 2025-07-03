package com.feature.category.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.category.ui.screens.CategoryScreen

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface CategoryNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 */
internal class CategoryNavigationImpl : CategoryNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Category>(
            startDestination = Dest.Category
        ) {
            composable<Dest.Category> {
                CategoryScreen()
            }
        }
    }

}