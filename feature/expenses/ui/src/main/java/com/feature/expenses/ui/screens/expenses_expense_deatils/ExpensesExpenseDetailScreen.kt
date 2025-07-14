package com.feature.expenses.ui.screens.expenses_expense_deatils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
        onDoneClick = onDoneClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesExpenseDetailScreenContent(
    uiState: EditExpenseScreenUiState,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        //showCategoryPickerDialog = true
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
                            onValueChange = {},//onAmountChanged,
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
                        //showDatePickerDialog = true
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
                        //showTimePickerDialog = true
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
                            onValueChange = {},//onCommentChanged,
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
//                DatePickerDialogComponent(
//                    showDialog = showDatePickerDialog,
//                    datePickerState = datePickerState,
//                    onDismiss = {
//                        showDatePickerDialog = false
//                    },
//                    onConfirm = { selectedDateLong ->
//                        onDateClick(selectedDateLong)
//                        showDatePickerDialog = false
//                    }
//                )
//                TimePickerDialogComponent(
//                    showDialog = showTimePickerDialog,
//                    timePickerState = timePickerState,
//                    onDismiss = {
//                        showTimePickerDialog = false
//                    },
//                    onConfirm = {
//                        onTimeClick(timePickerState)
//                    }
//                )
//                CategoryPickerDialog(
//                    categoriesList = uiState.categories,
//                    showDialog = showCategoryPickerDialog,
//                    onDismiss = {
//                        showCategoryPickerDialog = false
//                    },
//                    onConfirm = {
//                        onCategoryClick(it)
//                        showCategoryPickerDialog = false
//                    }
//                )
            }
        }
    }
}