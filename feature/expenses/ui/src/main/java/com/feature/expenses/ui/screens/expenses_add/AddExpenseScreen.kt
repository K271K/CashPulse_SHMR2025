package com.feature.expenses.ui.screens.expenses_add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.core.ui.theme.GreenPrimary
import com.feature.expenses.ui.screens.common.EditExpenseScreenUiState
import com.feature.expenses.ui.screens.common.TransactionCreationState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModelFactory: AddExpenseViewModelFactory,
    onNavigateBack: () -> Unit
) {
    val viewModel: AddExpenseViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        onNavigateBack()
    }

    val onSaveClick = remember {
        {
            viewModel.validateAndCreateTransaction(
                onSuccess = onNavigateBack,
                onValidationError = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    val onRetryClick = remember {
        {
            viewModel.retryTransaction(onSuccess = onNavigateBack)
        }
    }

    AddExpenseScreenContent(
        uiState = uiState,
        onCancelClick = onNavigateBack,
        onSaveClick = onSaveClick,
        onRetryClick = onRetryClick,
        onCategoryClick = remember { { category -> viewModel.selectCategory(category) } },
        onAmountChanged = remember { viewModel::setAmount },
        onDateClick = remember { { date -> viewModel.setDate(date) } },
        onTimeClick = remember { { time -> viewModel.setTime(time) } },
        onCommentChanged = remember { viewModel::setComment },

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreenContent(
    uiState: EditExpenseScreenUiState,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onRetryClick: () -> Unit,
    onCategoryClick: (CategoryPickerUiModel) -> Unit,
    onAmountChanged: (String) -> Unit,
    onDateClick: (Long) -> Unit,
    onTimeClick: (TimePickerState) -> Unit,
    onCommentChanged: (String) -> Unit,
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
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            trailingIcon = R.drawable.check,
            onLeadingIconClick = { onCancelClick() },
            onTrailingIconClick = { onSaveClick() }
        )
        when {
            uiState.error != null && uiState.transactionCreationState == TransactionCreationState.IDLE -> {
                MyErrorBox(
                    message = uiState.error
                )
            }

            uiState.isLoading -> {
                MyLoadingIndicator()
            }

            uiState.transactionCreationState == TransactionCreationState.LOADING -> {
                MyLoadingIndicator()
            }

            uiState.transactionCreationState == TransactionCreationState.SUCCESS -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Успешно!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = GreenPrimary
                    )
                }
            }

            uiState.transactionCreationState == TransactionCreationState.ERROR -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ошибка при создании транзакции",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    uiState.error?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(
                        onClick = onRetryClick,
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenPrimary
                        )
                    ) {
                        Text("Попробовать ещё раз")
                    }
                }
            }

            else -> {
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
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                        )
                    }
                )
                HorizontalDivider()
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
