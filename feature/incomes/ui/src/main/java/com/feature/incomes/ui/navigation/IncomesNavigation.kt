package com.feature.incomes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.incomes.ui.screens.incomes_history.IncomesHistoryScreen
import com.feature.incomes.ui.screens.incomes_today.IncomesTodayScreen

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface IncomesNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 */
internal class IncomesNavigationImpl : IncomesNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Incomes>(
            startDestination = Dest.IncomesToday
        ) {
            composable<Dest.IncomesToday> {
                IncomesTodayScreen(
                    onGoToHistoryClick = {
                        navHostController.navigate(Dest.IncomesHistory)
                    },
                    onGoToIncomeDetailScreen = {

                    }
                )
            }
            composable<Dest.IncomesHistory> {
                IncomesHistoryScreen(
                    onGoBackClick = {
                        navHostController.popBackStack()
                    },
                    onGoToAnalyticsClick = {

                    }
                )
            }
        }
    }

}