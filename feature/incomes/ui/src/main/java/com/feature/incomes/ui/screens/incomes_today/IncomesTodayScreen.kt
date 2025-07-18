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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyFloatingActionButton
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun IncomesTodayScreen(
    viewModelFactory: IncomesTodayViewModelFactory,
    onGoToHistoryClick: () -> Unit,
    onGoToIncomeDetailScreen: (Int)-> Unit,
    onGoToAddIncomeClick: () -> Unit,
) {
    val viewModel: IncomesTodayViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.incomesTodayScreenState.collectAsStateWithLifecycle()
    IncomesTodayScreenContent(
        uiState = uiState,
        onGoToHistoryClick = onGoToHistoryClick,
        onGoToIncomeDetailScreen = onGoToIncomeDetailScreen,
        navigateToAddIncomeScreen = onGoToAddIncomeClick
    )
}

@Composable
fun IncomesTodayScreenContent(
    uiState: IncomesTodayScreenState,
    onGoToHistoryClick: () -> Unit,
    onGoToIncomeDetailScreen: (Int)-> Unit,
    navigateToAddIncomeScreen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                            Text(text = "${uiState.data.totalAmount} ${uiState.data.currency}")
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
                                    Text(text = "${income.amount} ${income.currency}")
                                    Icon(
                                        painter = painterResource(R.drawable.more_right),
                                        contentDescription = null,
                                    )
                                },
                                onClick = {
                                    onGoToIncomeDetailScreen(income.id)
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
        MyFloatingActionButton(
            onClick = navigateToAddIncomeScreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}