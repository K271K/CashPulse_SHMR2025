package com.feature.expenses.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.core.navigation.Dest
import com.core.navigation.Feature
import com.core.navigation.SubGraphDest
import com.feature.expenses.ui.screens.ExpensesExpenseDetailScreen
import com.feature.expenses.ui.screens.expenses_history.ExpensesHistoryScreen
import com.feature.expenses.ui.screens.expenses_today.ExpensesTodayScreen

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
 * Детализация конкретного расхода (на будущее)
 */
internal class ExpensesNavigationImpl : ExpensesNavigation {
    override fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<SubGraphDest.Expenses>(
            startDestination = Dest.ExpensesToday
        ) {
            composable<Dest.ExpensesToday> {
                ExpensesTodayScreen(
                    onGoToHistoryClick = {
                        navHostController.navigate(Dest.ExpensesHistory)
                    },
                    onGoToExpenseDetailScreen = { expenseId->
                        navHostController.navigate(Dest.ExpensesExpenseDetails(id = expenseId))
                    }
                )
            }
            composable<Dest.ExpensesHistory> {
                ExpensesHistoryScreen(
                    onGoBackClick = {
                        navHostController.popBackStack()
                    },
                    onGoToAnalyticsClick = {

                    }
                )
            }
            composable<Dest.ExpensesExpenseDetails> {
                val args = it.toRoute<Dest.ExpensesExpenseDetails>()
                ExpensesExpenseDetailScreen(
                    expenseId = args.id,
                    onCancelClick = {
                        navHostController.popBackStack()
                    },
                    onDoneClick = {

                    }
                )
            }
        }
    }

}