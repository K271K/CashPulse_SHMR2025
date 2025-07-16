package com.feature.expenses.ui.screens.expenses_edit

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
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
import com.core.ui.theme.GreenPrimary
import com.feature.expenses.ui.screens.common.EditExpenseScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    expenseId: Int,
    viewModelFactory: EditExpenseViewModelFactory,
    onNavigateBack: () -> Unit,
) {
    val viewModel: EditExpenseScreenViewModel = viewModel(
        factory = viewModelFactory
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(expenseId) {
        viewModel.initWithId(
            expenseId = expenseId
        )
    }

    BackHandler {
        onNavigateBack()
    }

    val context = LocalContext.current

    val onUpdateTransactionClick = remember {
        {
            viewModel.validateAndUpdateTransaction(
                expenseId = expenseId,
                onValidationError = { errorMessage ->
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    val onDeleteTransactionClick = remember {
        {
            viewModel.deleteTransaction(
                expenseId = expenseId,
            )
        }
    }

    ExpensesExpenseDetailScreenContent(
        uiState = uiState,
        onCancelClick = {
            onNavigateBack()
        },
        onAmountChange = {
            viewModel.updateAmount(amount = it)
        },
        onDateChange = { selectedDateInMillis ->
            viewModel.updateDate(dateInMillis = selectedDateInMillis)
        },
        onCategoryChange = { selectedCategory ->
            viewModel.updateCategory(
                category = selectedCategory
            )
        },
        onTimeChange = { hour, minute ->
            viewModel.updateTime(hour = hour, minute = minute)
        },
        onCommentChange = {
            viewModel.updateComment(comment = it)
        },
        onUpdateTransactionClick = {
            onUpdateTransactionClick()
        },
        onDeleteTransactionClick = {
            onDeleteTransactionClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesExpenseDetailScreenContent(
    uiState: EditExpenseScreenUiState,
    onCancelClick: () -> Unit,
    afterSuccessUpdated: () -> Unit = onCancelClick,
    onAmountChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onCategoryChange: (CategoryPickerUiModel) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onUpdateTransactionClick: () -> Unit,
    onDeleteTransactionClick: () -> Unit
) {
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState()

    var showCategoryPickerDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            onLeadingIconClick = {
                onCancelClick()
            },
            trailingIcon = R.drawable.check,
            onTrailingIconClick = {
                onUpdateTransactionClick()
            }
        )
        when {
            uiState.isLoading -> {
                MyLoadingIndicator()
            }
            uiState.error != null -> {
                MyErrorBox(
                    message = uiState.error,
                    onRetryClick = {
                        onUpdateTransactionClick()
                    }
                )
            }
            uiState.success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Операция прошла успешно",
                        )
                        TextButton(
                            onClick = {
                                afterSuccessUpdated()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = GreenPrimary
                            )
                        ) {
                            Text(text = "Назад")
                        }
                    }
                }
            }

            else -> {
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(
                            text = "Счёт"
                        )
                    },
                    trailContent = {
                        Text(
                            text = uiState.accountName
                        )
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = "Bank account name"
                        )
                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    leadingText = "Статья",
                    trailingText = uiState.categoryName,
                    trailIcon = {
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = "Bank account name"
                        )
                    },
                    onClick = {
                        showCategoryPickerDialog = true
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(
                            text = "Сумма"
                        )
                    },
                    trailContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = "${uiState.amount}",
                                onValueChange = {
                                    onAmountChange(it)
                                },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    textAlign = TextAlign.End
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true,
                            )
                            Text(
                                text = uiState.currency,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 4.dp) // Небольшой отступ от поля
                            )
                        }
                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    leadingText = "Дата",
                    trailingText = uiState.expenseDate,
                    onClick = {
                        showDatePickerDialog = true
                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    leadingText = "Время",
                    trailingText = uiState.expenseTime,
                    onClick = {
                        showTimePickerDialog = true
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Комментарий")
                    },
                    trailContent = {
                        BasicTextField(
                            value = uiState.comment,
                            onValueChange = {
                                onCommentChange(it)
                            },
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.End
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
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
                        onDeleteTransactionClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = "Удалить расход")
                }
                CategoryPickerDialog(
                    categoriesList = uiState.categories,
                    showDialog = showCategoryPickerDialog,
                    onDismiss = {
                        showDatePickerDialog = false
                    },
                    onConfirm = { selectedCategory ->
                        onCategoryChange(selectedCategory)
                        showCategoryPickerDialog = false
                    }
                )
                DatePickerDialogComponent(
                    showDialog = showDatePickerDialog,
                    datePickerState = datePickerState,
                    onConfirm = {
                        val selectedDateInMillis = datePickerState.selectedDateMillis
                        selectedDateInMillis?.let {
                            onDateChange(it)
                        }
                        showDatePickerDialog = false
                    },
                    onDismiss = {
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
                        onTimeChange(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        showTimePickerDialog = false
                    },
                )
            }
        }
    }
}