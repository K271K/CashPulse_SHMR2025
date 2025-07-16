package com.feature.incomes.ui.screens.incomes_add

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.core.ui.R
import com.core.ui.components.MyTopAppBar

@Composable
fun AddIncomeScreen(
    navigateBackToHistory: () -> Unit,
) {
    BackHandler {
        navigateBackToHistory()
    }
    AddIncomeScreenContent(
        navigateBackToHistory = navigateBackToHistory,
    )
}

@Composable
private fun AddIncomeScreenContent(
    navigateBackToHistory: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTopAppBar(
            text = "Мои доходы",
            leadingIcon = R.drawable.cross,
            onLeadingIconClick = {
                navigateBackToHistory()
            },
            trailingIcon = R.drawable.check,
            onTrailingIconClick = {

            }
        )
    }
}