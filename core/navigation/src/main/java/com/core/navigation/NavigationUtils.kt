package com.core.navigation

import kotlinx.serialization.Serializable

/**
 * Здесь описаны верхнеуровневые маршруты. 5 элементов нижней навигации -- 5 верхнеуровневых маршрутов.
 */
sealed class SubGraphDest(){

    @Serializable
    data object Expenses: SubGraphDest()

    @Serializable
    data object Incomes: SubGraphDest()

    @Serializable
    data object Account: SubGraphDest()

    @Serializable
    data object Category: SubGraphDest()

    @Serializable
    data object Settings: SubGraphDest()
}

/**
 * Здесь описаны вложенные графы навигации. У каждой фичи может быть своя навигация.
 *
 * Пример:
 * Верхнеуровневый маршрут Expenses.
 *
 * Вложенные маршруты у Expenses: ExpensesToday, ExpensesHistory, ExpensesExpenseDetails.
 */
sealed class Dest() {

    @Serializable
    data object ExpensesToday: Dest()

    @Serializable
    data object ExpensesHistory: Dest()

    @Serializable
    data class ExpensesExpenseDetails(val id: Int): Dest()

    @Serializable
    data object IncomesToday: Dest()

    @Serializable
    data object IncomesHistory: Dest()

    @Serializable
    data class IncomesIncomeDetails(val id: Int): Dest()

    @Serializable
    data object AccountMain: Dest()

    @Serializable
    data class AccountEdit(val id: Int): Dest()

    @Serializable
    data object Category: Dest()

    @Serializable
    data object Settings: Dest()
}