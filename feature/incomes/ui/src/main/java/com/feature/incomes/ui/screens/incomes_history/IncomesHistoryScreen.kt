package com.feature.incomes.ui.screens.incomes_history

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.DatePickerDialogComponent
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyPickerRow
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun IncomesHistoryScreen(
    viewModelFactory: IncomesHistoryViewModelFactory,
    onGoBackClick: () -> Unit,
    onGoToAnalyticsClick: () -> Unit,
    onGoToIncomeDetailScreen: (Int) -> Unit
) {
    val viewModel: IncomesHistoryViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.incomesHistoryScreenState.collectAsStateWithLifecycle()

    IncomesHistoryScreenContent(
        uiState = uiState,
        onChooseStartDate = {
            viewModel.updateStartDate(it)
        },
        onChooseEndDate = {
            viewModel.updateEndDate(it)
        },
        onGoBackClick = onGoBackClick,
        onGoToAnalyticsClick = onGoToAnalyticsClick,
        onGoToIncomeDetailScreen = onGoToIncomeDetailScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomesHistoryScreenContent(
    uiState: IncomesHistoryScreenState,
    onChooseStartDate: (Long) -> Unit,
    onChooseEndDate: (Long) -> Unit,
    onGoBackClick: () -> Unit,
    onGoToAnalyticsClick: () -> Unit,
    onGoToIncomeDetailScreen: (Int) -> Unit
) {
    var showStartDatePickerDialog by remember { mutableStateOf(false) }
    var showEndDatePickerDialog by remember { mutableStateOf(false) }
    val startDatePickerState = rememberDatePickerState()
    val endDatePickerState = rememberDatePickerState()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MyTopAppBar(
            text = "Моя история",
            leadingIcon = R.drawable.back,
            onLeadingIconClick = {
                onGoBackClick.invoke()
            },
            trailingIcon = R.drawable.clipboard_text,
            onTrailingIconClick = {
                onGoToAnalyticsClick.invoke()
            }
        )
        when (uiState) {
            is IncomesHistoryScreenState.Error -> {
                MyErrorBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = uiState.message
                )
            }

            is IncomesHistoryScreenState.Loaded -> {
                MyPickerRow(
                    trailingText = "Начало",
                    leadingText = uiState.data.startDate,
                    onClick = {
                        showStartDatePickerDialog = true
                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    trailingText = "Конец",
                    leadingText = uiState.data.endDate,
                    onClick = {
                        showEndDatePickerDialog = true
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    content = {
                        Text(
                            text = "Сумма"
                        )
                    },
                    trailContent = {
                        Text(uiState.data.totalAmount)
                    },
                )

                if (uiState.data.incomes.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет расходов за выбранный период",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        items(
                            uiState.data.incomes,
                            key = { it.id }) {
                            MyListItemWithLeadIcon(
                                modifier = Modifier
                                    .height(70.dp),
                                icon = it.emojiData.toString(),
                                iconBg = GreenLight,
                                content = {
                                    Column {
                                        Text(
                                            text = it.name
                                        )
                                        if (it.description?.isNotEmpty() == true) {
                                            Text(
                                                text = it.description,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                },
                                trailContent = {
                                    Column(
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        Text(text = "${it.amount} ${it.currency}")
                                        Text(text = it.time)
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.more_right),
                                        contentDescription = null,
                                    )
                                },
                                onClick = {
                                    onGoToIncomeDetailScreen(it.id)
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }

            IncomesHistoryScreenState.Loading -> {
                Column {
                    // Показываем заголовки даже во время загрузки
                    MyPickerRow(
                        trailingText = "Начало",
                        leadingText = "Загрузка...",
                        onClick = { }
                    )
                    HorizontalDivider()
                    MyPickerRow(
                        trailingText = "Конец",
                        leadingText = "Загрузка...",
                        onClick = { }
                    )
                    HorizontalDivider()
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        DatePickerDialogComponent(
            showDialog = showStartDatePickerDialog,
            datePickerState = startDatePickerState,
            onDismiss = { showStartDatePickerDialog = false },
            onConfirm = { selectedDate ->
                onChooseStartDate(selectedDate)
                showStartDatePickerDialog = false
            }
        )

        DatePickerDialogComponent(
            showDialog = showEndDatePickerDialog,
            datePickerState = endDatePickerState,
            onDismiss = { showEndDatePickerDialog = false },
            onConfirm = { selectedDate ->
                onChooseEndDate(selectedDate)
                showEndDatePickerDialog = false
            }
        )
    }
}