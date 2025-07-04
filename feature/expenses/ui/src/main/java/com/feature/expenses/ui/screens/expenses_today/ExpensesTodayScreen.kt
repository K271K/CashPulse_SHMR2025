package com.feature.expenses.ui.screens.expenses_today

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
import androidx.compose.material3.MaterialTheme
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
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun ExpensesTodayScreen(
    viewModel: ExpensesTodayViewModel = hiltViewModel(),
    onGoToHistoryClick: () -> Unit,
    onGoToExpenseDetailScreen: (Int) -> Unit
) {
    val uiState: ExpensesTodayScreenState by viewModel.expensesTodayScreenState.collectAsStateWithLifecycle()
    ExpensesTodayScreenContent(
        uiState = uiState,
        onGoToHistoryClick = onGoToHistoryClick,
        onGoToExpenseDetailScreen = onGoToExpenseDetailScreen,
    )
}

@Composable
fun ExpensesTodayScreenContent(
    uiState: ExpensesTodayScreenState,
    onGoToHistoryClick: () -> Unit,
    onGoToExpenseDetailScreen: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Расходы сегодня",
            trailingIcon = R.drawable.history,
            onTrailingIconClick = {
                onGoToHistoryClick.invoke()
            }
        )
        when (uiState) {
            is ExpensesTodayScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ExpensesTodayScreenState.Error -> {
                MyErrorBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = uiState.message
                )
            }

            is ExpensesTodayScreenState.ErrorResource -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "error")
                }
            }

            is ExpensesTodayScreenState.Loaded -> {
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
                        .padding(bottom = 16.dp),
                ) {
                    items(uiState.data.expenses, key = { it.id }) {
                        MyListItemWithLeadIcon(
                            modifier = Modifier
                                .height(70.dp),
                            icon = it.icon,
                            iconBg = GreenLight,
                            content = {
                                Column {
                                    Text(
                                        text = it.category
                                    )
                                    if (it.comment.isNotEmpty()) {
                                        Text(
                                            text = it.comment,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            },
                            trailContent = {
                                Text(text = it.amount + " R")
                            },
                            onClick = {
                                onGoToExpenseDetailScreen.invoke(it.id)
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}