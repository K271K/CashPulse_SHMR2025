package com.feature.expenses.ui.screens.expenses_edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextButton
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
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyPickerRow
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    viewModelFactory: EditExpenseViewModelFactory,
    expenseId: Int,
    onNavigateBack: () -> Unit
) {

    val viewModel: EditExpenseViewModel = viewModel(
        factory = viewModelFactory
    )
    val uiState by viewModel.editExpenseScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(expenseId) {
        viewModel.initWithExpenseId(expenseId)
    }

    BackHandler {
        onNavigateBack()
    }

    ExpensesExpenseDetailScreenContent(
        uiState = uiState,
        onCancelClick = onNavigateBack
    )
}

@Composable
fun ExpensesExpenseDetailScreenContent(
    uiState: EditExpenseScreenUiState,
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    var amount by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            onLeadingIconClick = {
                onCancelClick()
            },
            trailingIcon = R.drawable.check,
            onTrailingIconClick = {
                onSaveClick()
            }
        )
        when (uiState) {
            EditExpenseScreenUiState.Loading -> {
                MyLoadingIndicator()
            }

            EditExpenseScreenUiState.Updating -> {
                MyLoadingIndicator()
            }

            is EditExpenseScreenUiState.Error -> {
                MyErrorBox(
                    message = uiState.message,
                    onRetryClick = { },
                )
            }
            is EditExpenseScreenUiState.Success -> {
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
            is EditExpenseScreenUiState.Loaded -> {
                amount = uiState.data.amount
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
                            text = "${uiState.data.accountName}"
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
                    trailingText = uiState.data.categoryName,
                    trailIcon = {
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = "Bank account name"
                        )
                    },
                    onClick = {

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
                        BasicTextField(
                            value = amount,
                            onValueChange = {
                                amount = it
                            },
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.End
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true
                        )
                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    leadingText = "Дата",
                    trailingText = uiState.data.expenseDate,
                    onClick = {

                    }
                )
                HorizontalDivider()
                MyPickerRow(
                    modifier = Modifier
                        .height(70.dp),
                    leadingText = "Время",
                    trailingText =  uiState.data.expenseTime,
                    onClick = {

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
                            value = uiState.data.comment,
                            onValueChange = {},
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
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(
                        text = "Удалить расход"
                    )
                }
            }
        }
    }
}