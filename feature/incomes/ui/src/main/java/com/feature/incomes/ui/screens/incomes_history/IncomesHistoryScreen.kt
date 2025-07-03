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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.R
import com.core.ui.components.DatePickerDialogComponent
import com.core.ui.components.MyDatePickerRow
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun IncomesHistoryScreen(
    viewModel: IncomesHistoryViewModel = hiltViewModel(),
    onGoBackClick: () -> Unit,
    onGoToAnalyticsClick: () -> Unit
) {
    val uiState: IncomesHistoryScreenState by viewModel.incomesHistoryScreenState.collectAsStateWithLifecycle()

    IncomesHistoryScreenContent(
        uiState = uiState,
        onChooseStartDate = viewModel::updateStartDate,
        onChooseEndDate = viewModel::updateEndDate,
        onGoBackClick = onGoBackClick,
        onGoToAnalyticsClick = onGoToAnalyticsClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomesHistoryScreenContent(
    uiState: IncomesHistoryScreenState,
    onChooseStartDate: (Long) -> Unit,
    onChooseEndDate: (Long) -> Unit,
    onGoBackClick: () -> Unit,
    onGoToAnalyticsClick: () -> Unit
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
                MyDatePickerRow(
                    trailingText = "Начало",
                    leadingText = uiState.data.startDate,
                    onClick = {
                        showStartDatePickerDialog = true
                    }
                )
                HorizontalDivider()
                MyDatePickerRow(
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

                if (uiState.data.listOfIncomes.isEmpty()) {
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
                            uiState.data.listOfIncomes,
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
                                        Text(text = it.amount + " R")
                                        Text(text = it.time)
                                    }
                                },
                                onClick = {

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
                    MyDatePickerRow(
                        trailingText = "Начало",
                        leadingText = "Загрузка...",
                        onClick = { }
                    )
                    HorizontalDivider()
                    MyDatePickerRow(
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