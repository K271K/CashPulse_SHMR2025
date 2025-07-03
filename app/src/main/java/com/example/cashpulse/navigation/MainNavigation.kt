package com.example.cashpulse.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.navigation.SubGraphDest
import com.example.cashpulse.ui.components.MyBottomAppBar
import com.example.cashpulse.ui.components.MyFloatingActionButton

/**
 * BottomBar и FloatingActionButton задаются через Scaffold.
 * TopBar каждая фича рисует сама без использования Scaffold и Material3 TopAppBar.
 */
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    defaultNavigator: DefaultNavigator
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MyBottomAppBar(
                navController = navController
            )
        },
        floatingActionButton = {
            MyFloatingActionButton(
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            startDestination = SubGraphDest.Expenses,
            navController = navController,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            /**
             * Здесь происходит регистрация каждой фичи для навигации
             */
            defaultNavigator.expenses.registerGraph(
                navHostController = navController,
                navGraphBuilder = this
            )
            defaultNavigator.incomes.registerGraph(
                navHostController = navController,
                navGraphBuilder = this
            )
            defaultNavigator.account.registerGraph(
                navHostController = navController,
                navGraphBuilder = this
            )
            defaultNavigator.category.registerGraph(
                navHostController = navController,
                navGraphBuilder = this
            )
            defaultNavigator.settings.registerGraph(
                navHostController = navController,
                navGraphBuilder = this
            )
        }
    }
}