package com.feature.incomes.ui.screens.incomes_today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun IncomesTodayScreen(
    viewModel: IncomesTodayViewModel = hiltViewModel(),
    onGoToHistoryClick: () -> Unit,
    onGoToIncomeDetailScreen: (Int)-> Unit
) {
    val uiState: IncomesTodayScreenState by viewModel.incomesTodayScreenState.collectAsStateWithLifecycle()
    IncomesTodayScreenContent(
        uiState = uiState,
        onGoToHistoryClick = onGoToHistoryClick,
        onGoToExpenseDetailScreen = onGoToIncomeDetailScreen
    )
}

@Composable
fun IncomesTodayScreenContent(
    uiState: IncomesTodayScreenState,
    onGoToHistoryClick: () -> Unit,
    onGoToExpenseDetailScreen: (Int)-> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Доходы сегодня",
            trailingIcon = R.drawable.history,
            onTrailingIconClick = {
                onGoToHistoryClick()
            }
        )
        when (uiState) {
            is IncomesTodayScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is IncomesTodayScreenState.Error -> {
                MyErrorBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = uiState.message
                )
            }
            is IncomesTodayScreenState.Loaded -> {
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    content = {
                        Text(text = "Всего")
                    },
                    trailContent = {
                        Text(text = uiState.data.totalAmount + " R")
                    }
                )
                HorizontalDivider()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    items(
                        items = uiState.data.incomes,
                        key = { it.id }
                    ) { income ->
                        MyListItemOnlyText(
                            modifier = Modifier
                                .height(72.dp),
                            content = {
                                Text(text = income.categoryName)
                            },
                            trailContent = {
                                Text(text = income.amount + " R")
                            },
                            onClick = null
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}