package com.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Интерфейс Feature определяет метод для регистрации графа навигации.
 * Модуль :core:navigation подключен к каждой фиче и к :app модулю
 * Каждая фича наследуется от Feature интерфейса и определяет свой личный открытый интерфейс, а также закрытую реализацию этого интерфейса.
 *
 * Конкретные примеры можно увидеть в модулях фич в папке navigation
 */
interface Feature {

    fun registerGraph(
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )
}