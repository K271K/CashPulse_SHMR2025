package com.feature.incomes.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.incomes.ui.screens.incomes_add.IncomesAddScreen
import com.feature.incomes.ui.screens.incomes_add.IncomesAddScreenViewModelFactory
import com.feature.incomes.ui.screens.incomes_edit.IncomesEditScreen
import com.feature.incomes.ui.screens.incomes_edit.IncomesEditScreenViewModelFactory
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
    private val incomesHistoryViewModelFactory: IncomesHistoryViewModelFactory,
    private val incomesAddViewModelFactory: IncomesAddScreenViewModelFactory,
    private val incomesEditViewModelFactory: IncomesEditScreenViewModelFactory
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
                    onGoToIncomeDetailScreen = { incomeId ->
                        navHostController.navigate(Dest.IncomesIncomeDetails(id = incomeId)) {
                            launchSingleTop = true
                            popUpTo(Dest.IncomesToday) {
                                inclusive = true
                            }
                        }
                    },
                    onGoToAddIncomeClick = {
                        navHostController.navigate(Dest.IncomesAdd) {
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

                    },
                    onGoToIncomeDetailScreen = { incomeId ->
                        navHostController.navigate(Dest.IncomesIncomeDetails(id = incomeId)) {
                            launchSingleTop = true
                            popUpTo(Dest.IncomesToday) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Dest.IncomesAdd> {
                IncomesAddScreen(
                    onNavigateBack = {
                        navHostController.navigate(Dest.IncomesToday) {
                            launchSingleTop = true
                            popUpTo(Dest.IncomesAdd) {
                                inclusive = true
                            }
                        }
                    },
                    viewModelFactory = incomesAddViewModelFactory,
                )
            }
            composable<Dest.IncomesIncomeDetails> {
                val args = it.toRoute<Dest.ExpensesExpenseDetails>()
                IncomesEditScreen(
                    incomeId = args.id,
                    viewModelFactory = incomesEditViewModelFactory,
                    onNavigateBack = {
                        navHostController.navigate(Dest.IncomesToday) {
                            launchSingleTop = true
                            popUpTo(Dest.ExpensesExpenseDetails(args.id)) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
    }

}