package com.feature.incomes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.incomes.ui.screens.incomes_add.AddIncomeScreen
import com.feature.incomes.ui.screens.incomes_history.IncomesHistoryScreen
import com.feature.incomes.ui.screens.incomes_history.IncomesHistoryViewModelFactory
import com.feature.incomes.ui.screens.incomes_today.IncomesTodayScreen
import com.feature.incomes.ui.screens.incomes_today.IncomesTodayViewModelFactory
import javax.inject.Inject

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface IncomesNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 */
internal class IncomesNavigationImpl @Inject constructor(
    private val incomesTodayViewModelFactory: IncomesTodayViewModelFactory,
    private val incomesHistoryViewModelFactory: IncomesHistoryViewModelFactory
) : IncomesNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Incomes>(
            startDestination = Dest.IncomesToday
        ) {
            composable<Dest.IncomesToday> {
                IncomesTodayScreen(
                    viewModelFactory = incomesTodayViewModelFactory,
                    onGoToHistoryClick = {
                        navHostController.navigate(Dest.IncomesHistory)
                    },
                    onGoToIncomeDetailScreen = {

                    },
                    navigateToAddIncomeScreen = {
                        navHostController.navigate(Dest.IncomesAdd){
                            launchSingleTop = true
                            popUpTo(Dest.IncomesToday) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Dest.IncomesHistory> {
                IncomesHistoryScreen(
                    viewModelFactory = incomesHistoryViewModelFactory,
                    onGoBackClick = {
                        navHostController.popBackStack()
                    },
                    onGoToAnalyticsClick = {

                    }
                )
            }
            composable<Dest.IncomesAdd> {
                AddIncomeScreen (
                    navigateBackToHistory = {
                        navHostController.navigate(Dest.IncomesToday) {
                            launchSingleTop = true
                            popUpTo(Dest.IncomesAdd) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }

}