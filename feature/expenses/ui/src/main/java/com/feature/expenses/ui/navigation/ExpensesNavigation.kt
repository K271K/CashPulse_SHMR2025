package com.feature.expenses.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.expenses.ui.screens.expenses_add.ExpensesAddScreen
import com.feature.expenses.ui.screens.expenses_add.AddExpenseViewModelFactory
import com.feature.expenses.ui.screens.expenses_edit.ExpensesEditScreen
import com.feature.expenses.ui.screens.expenses_edit.EditExpenseViewModelFactory
import com.feature.expenses.ui.screens.expenses_history.ExpensesHistoryScreen
import com.feature.expenses.ui.screens.expenses_history.ExpensesHistoryViewModelFactory
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayScreen
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayViewModelFactory
import javax.inject.Inject

/**
 * Наследуемся от интерфейса Feature из :core:navigation
 * Интерфейс фичи открытый и используется в :app модуле для регистрации фичи в навигации
 */
interface ExpensesNavigation : Feature

/**
 * internal имплементация интерфейса фичи, которая непосредственно задаёт граф навигации для фичи
 *
 *
 * Конкретно у фичи Expenses я сделал три экрана:
 * Расходы сегодня
 * История расходов
 * Детализация конкретного расхода
 */
internal class ExpensesNavigationImpl @Inject constructor(
    private val expensesTodayViewModelFactory: ExpensesTodayViewModelFactory,
    private val expensesHistoryViewModelFactory: ExpensesHistoryViewModelFactory,
    private val addExpenseViewModelFactory: AddExpenseViewModelFactory,
    private val editExpenseViewModelFactory: EditExpenseViewModelFactory
) : ExpensesNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Expenses>(
            startDestination = Dest.ExpensesToday
        ) {
            composable<Dest.ExpensesToday> {
                ExpensesTodayScreen(
                    viewModelFactory = expensesTodayViewModelFactory,
                    onGoToHistoryClick = {
                        navHostController.navigate(Dest.ExpensesHistory) {
                            popUpTo(Dest.ExpensesToday) {
                                inclusive = true
                            }
                        }
                    },
                    onGoToAddExpenseClick = {
                        navHostController.navigate(Dest.ExpensesAdd) {
                            launchSingleTop = true
                            popUpTo(Dest.ExpensesToday) {
                                inclusive = true
                            }
                        }
                    },
                    onGoToExpenseDetailScreen = { expenseId ->
                        navHostController.navigate(Dest.ExpensesExpenseDetails(id = expenseId)) {
                            launchSingleTop = true
                            popUpTo(Dest.ExpensesToday) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Dest.ExpensesHistory> {
                ExpensesHistoryScreen(
                    viewModelFactory = expensesHistoryViewModelFactory,
                    onGoBackClick = {
                        navHostController.navigate(Dest.ExpensesToday) {
                            popUpTo(Dest.ExpensesHistory) {
                                inclusive = true
                            }
                        }
                    },
                    onGoToAnalyticsClick = {

                    },
                    onGoToExpenseDetailScreen = { expenseId ->

                        navHostController.navigate(Dest.ExpensesExpenseDetails(id = expenseId)) {
                            launchSingleTop = true
                            popUpTo(Dest.ExpensesHistory) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Dest.ExpensesExpenseDetails> {
                val args = it.toRoute<Dest.ExpensesExpenseDetails>()
                ExpensesEditScreen(
                    expenseId = args.id,
                    viewModelFactory = editExpenseViewModelFactory,
                    onNavigateBack = {
                        navHostController.navigate(Dest.ExpensesToday){
                            launchSingleTop = true
                            popUpTo(Dest.ExpensesExpenseDetails(args.id)){
                                inclusive = true
                            }
                        }
                    },
                )
            }
            composable<Dest.ExpensesAdd> {
                ExpensesAddScreen(
                    viewModelFactory = addExpenseViewModelFactory,
                    onNavigateBack = {
                        navHostController.navigate(Dest.ExpensesToday) {
                            popUpTo(Dest.ExpensesAdd) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}