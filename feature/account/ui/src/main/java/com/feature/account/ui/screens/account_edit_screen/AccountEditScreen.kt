package com.feature.account.ui.screens.account_edit_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun AccountEditScreen(
    viewModelFactory: AccountEditViewModelFactory,
    accountId: Int,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    val viewModel: AccountEditViewModel = viewModel(factory = viewModelFactory)

    LaunchedEffect(accountId) { viewModel.loadAccount(accountId) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AccountEditScreenContent(
        uiState = uiState,
        onNameChanged = viewModel::updateAccountName,
        onBalanceChanged = viewModel::updateAccountBalance,
        onCurrencyChanged = viewModel::updateAccountCurrency,
        onCancelClick = onCancelClick,
        onDoneClick = {
            viewModel.updateAccountData()
            //onDoneClick()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountEditScreenContent(
    uiState: EditAccountUiState,
    onNameChanged: (String) -> Unit,
    onBalanceChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    var showCurrencySheet by remember { mutableStateOf(false) }
    when {
        uiState.errorMessage != null -> {
            MyErrorBox(
                message = "${uiState.errorMessage}",
                modifier = Modifier.fillMaxSize()
            )
        }
        uiState.isLoading -> {
            MyLoadingIndicator()
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MyTopAppBar(
                    text = "Редактирование счёта",
                    leadingIcon = R.drawable.cross,
                    trailingIcon = R.drawable.check,
                    onLeadingIconClick = {
                        onCancelClick()
                    },
                    onTrailingIconClick = {
                        onDoneClick()
                    },
                )
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    content = {
                        Text(text = "Название счёта")
                    },
                    trailContent = {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = uiState.name,
                            onValueChange = { onNameChanged(it) },
                            colors = TextFieldDefaults.colors().copy(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.End
                            )
                        )
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    content = {
                        Text(text = "Баланс")
                    },
                    trailContent = {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = uiState.balance,
                            onValueChange = { onBalanceChanged(it) },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors().copy(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.End
                            ),
                        )
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    content = {
                        Text(text = "Валюта")
                    },
                    trailContent = {
                        Text(
                            text = uiState.currency,
                            modifier = Modifier.padding(end = 16.dp),
                        )
                    },
                    onClick = { showCurrencySheet = true }
                )
                if (showCurrencySheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showCurrencySheet = false }
                    ) {
                        Currency.values().forEach {
                            ListItem(
                                modifier = Modifier.clickable {
                                    onCurrencyChanged(it.name)
                                    showCurrencySheet = false
                                },
                                headlineContent = { Text(it.displayName) }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class Currency(val displayName: String) {
    USD("USD"),
    EUR("EUR"),
    RUB("RUB")
}
