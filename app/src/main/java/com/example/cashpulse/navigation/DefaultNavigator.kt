package com.example.cashpulse.navigation

import com.feature.account.ui.navigation.AccountNavigation
import com.feature.category.ui.navigation.CategoryNavigation
import com.feature.expenses.ui.navigation.ExpensesNavigation
import com.feature.incomes.ui.navigation.IncomesNavigation
import com.feature.settings.ui.navigation.SettingsNavigation

/**
 * Каждая переменная представлена интерфейсом, который наследуется от интерфейса Feature (:core:navigation модуль)
 * В каждом модуле есть реализация соответствующего интерфейса, в которой описывается граф навигации для каждой фичи
 * Грубо говоря переменные которые есть тут это 5 основных маршрутов навигации. Но каждый из них ещё содержит подграф
 */
data class DefaultNavigator(
    val expenses: ExpensesNavigation,
    val incomes: IncomesNavigation,
    val account: AccountNavigation,
    val category: CategoryNavigation,
    val settings: SettingsNavigation
)