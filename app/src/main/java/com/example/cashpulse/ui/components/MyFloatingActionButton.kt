package com.example.cashpulse.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.core.navigation.Dest
import com.core.navigation.SubGraphDest
import com.example.cashpulse.ui.theme.GreenPrimary

@Composable
fun MyFloatingActionButton(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == Dest.ExpensesToday::class.qualifiedName ||
        currentRoute == Dest.IncomesToday::class.qualifiedName ||
        currentRoute == Dest.AccountMain::class.qualifiedName
    ) {
        FloatingActionButton(
            onClick = {
                when (currentRoute) {
                    Dest.ExpensesToday::class.qualifiedName -> {
                        navController.navigate(Dest.ExpensesAdd)
                    }

                    SubGraphDest.Incomes::class.qualifiedName -> {

                    }

                    SubGraphDest.Account::class.qualifiedName -> {

                    }
                }
            },
            modifier = Modifier.size(56.dp),
            shape = CircleShape,
            containerColor = GreenPrimary,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}