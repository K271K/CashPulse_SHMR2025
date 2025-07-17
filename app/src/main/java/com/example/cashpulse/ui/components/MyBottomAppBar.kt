package com.example.cashpulse.ui.components

import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.core.navigation.SubGraphDest
import com.example.cashpulse.navigation.bottomNavItems
import com.example.cashpulse.ui.theme.GreenLight
import com.example.cashpulse.ui.theme.GreenPrimary

@Composable
fun MyBottomAppBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = SubGraphDest.Expenses // Укажите ваш начальный маршрут
    BottomAppBar {
        bottomNavItems.forEach { item->
            NavigationBarItem(
                selected = navBackStackEntry?.destination?.hierarchy?.any{
                    it.route == item.route::class.qualifiedName
                } == true,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(item.route){
                            inclusive = true
                        }
                    }
                },
                label = { Text(text = item.label)},
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenPrimary,
                    indicatorColor = GreenLight
                )
            )
        }
    }
}