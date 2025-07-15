package com.feature.expenses.ui.screens.expenses_expense_deatils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.CategoryPickerDialog
import com.core.ui.components.DatePickerDialogComponent
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyPickerRow
import com.core.ui.components.MyTopAppBar
import com.core.ui.components.TimePickerDialogComponent
import com.core.ui.models.CategoryPickerUiModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesExpenseDetailScreen(
    expenseId: Int,
    viewModelFactory: EditExpenseViewModelFactory,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    val viewModel: EditExpenseViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(
        expenseId
    ) {
        viewModel.initFromComposable(expenseId)
    }
    ExpensesExpenseDetailScreenContent(
        uiState = uiState,
        onCancelClick = onCancelClick,
        onDoneClick = onDoneClick,
        onCategoryClick = { viewModel.selectCategory(it) },
        onAmountChanged = viewModel::setAmount,
        onDateClick = { viewModel.setDate(it) },
        onTimeClick = { viewModel.setTime(it) },
        onCommentChanged = viewModel::setComment
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesExpenseDetailScreenContent(
    uiState: EditExpenseScreenUiState,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    onCategoryClick: (CategoryPickerUiModel) -> Unit,
    onAmountChanged: (String) -> Unit,
    onDateClick: (Long) -> Unit,
    onTimeClick: (TimePickerState) -> Unit,
    onCommentChanged: (String) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showCategoryPickerDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            onLeadingIconClick = {
                onCancelClick.invoke()
            },
            trailingIcon = R.drawable.check,
            onTrailingIconClick = {
                onDoneClick.invoke()
            }
        )
        when {
            uiState.error != null -> {
                MyErrorBox(
                    message = uiState.error ?: "Unknown error"
                )
            }

            uiState.isLoading -> {
                MyLoadingIndicator()
            }

            else -> {
                // Счёт — неинтерактивный
                MyListItemOnlyText(
                    modifier = Modifier.height(70.dp),
                    content = { Text(text = "Счёт") },
                    trailContent = {
                        Text(text = "Сбербанк")
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                // Статья — категория
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    trailingText = uiState.selectedCategory?.name ?: "Выбрать",
                    leadingText = "Категория",
                    onClick = {
                        showCategoryPickerDialog = true
                    }
                )
                HorizontalDivider()
                // Сумма — интерактивная, TextField
                MyListItemOnlyText(
                    modifier = Modifier.height(70.dp),
                    content = { Text(text = "Сумма") },
                    trailContent = {
                        BasicTextField(
                            value = uiState.amount,
                            onValueChange = onAmountChanged,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.End
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                        )
                    }
                )
                HorizontalDivider()
                // Дата — интерактивная
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    trailingText = uiState.date.ifEmpty { "Выбрать" },
                    leadingText = "Дата",
                    onClick = {
                        showDatePickerDialog = true
                    }
                )
                HorizontalDivider()
                // Время — интерактивное
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    trailingText = uiState.time.ifEmpty { "Выбрать" },
                    leadingText = "Время",
                    onClick = {
                        showTimePickerDialog = true
                    }
                )
                HorizontalDivider()
                // Комментарий — интерактивный TextField
                MyListItemOnlyText(
                    modifier = Modifier.height(70.dp),
                    content = { Text(text = "Комментарий") },
                    trailContent = {
                        BasicTextField(
                            value = uiState.comment,
                            onValueChange = onCommentChanged,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.End
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                        )
                    }
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = "Удалить расход")
                }
                DatePickerDialogComponent(
                    showDialog = showDatePickerDialog,
                    datePickerState = datePickerState,
                    onDismiss = {
                        showDatePickerDialog = false
                    },
                    onConfirm = { selectedDateLong ->
                        onDateClick(selectedDateLong)
                        showDatePickerDialog = false
                    }
                )
                TimePickerDialogComponent(
                    showDialog = showTimePickerDialog,
                    timePickerState = timePickerState,
                    onDismiss = {
                        showTimePickerDialog = false
                    },
                    onConfirm = {
                        onTimeClick(timePickerState)
                    }
                )
                CategoryPickerDialog(
                    categoriesList = uiState.categories,
                    showDialog = showCategoryPickerDialog,
                    onDismiss = {
                        showCategoryPickerDialog = false
                    },
                    onConfirm = {
                        onCategoryClick(it)
                        showCategoryPickerDialog = false
                    }
                )
            }
        }
    }
}