package com.feature.account.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.accountScreenState.collectAsStateWithLifecycle()
    var showCurrencyBottomSheet by remember { mutableStateOf(false) }
    var showEditNameDialog by remember { mutableStateOf(false) }
    var showEditBalanceDialog by remember { mutableStateOf(false) }
    var editNameText by remember { mutableStateOf("") }
    var editBalanceText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        AccountScreenContent(
            uiState = uiState,
            onCurrencyClick = { showCurrencyBottomSheet = true },
            onEditNameClick = { currentName ->
                editNameText = currentName
                showEditNameDialog = true
            },
            onEditBalanceClick = { currentBalance ->
                editBalanceText = currentBalance
                showEditBalanceDialog = true
            }
        )

        // Overlay to close bottom sheet when clicking outside
        if (showCurrencyBottomSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { showCurrencyBottomSheet = false }
            )
        }

        AnimatedVisibility(
            visible = showCurrencyBottomSheet,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(362.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                CurrencySelectionBottomSheet(
                    onCurrencySelected = { currency ->
                        viewModel.updateAccountCurrency(currency)
                        showCurrencyBottomSheet = false
                    }
                )
            }
        }
    }

    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text(text = "Изменить название счёта") },
            text = {
                OutlinedTextField(
                    value = editNameText,
                    onValueChange = { editNameText = it },
                    label = { Text("Название счёта") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editNameText.isNotBlank()) {
                            viewModel.updateAccountName(editNameText)
                        }
                        showEditNameDialog = false
                    }
                ) {
                    Text("Подтвердить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditNameDialog = false }
                ) {
                    Text("Отменить")
                }
            }
        )
    }

    if (showEditBalanceDialog) {
        AlertDialog(
            onDismissRequest = { showEditBalanceDialog = false },
            title = { Text(text = "Изменить баланс") },
            text = {
                OutlinedTextField(
                    value = editBalanceText,
                    onValueChange = { newValue ->
                        // Разрешаем только цифры и точку/запятую для десятичных чисел
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*[.,]?\\d*$"))) {
                            editBalanceText = newValue
                        }
                    },
                    label = { Text("Баланс") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editBalanceText.isNotBlank()) {
                            // Заменяем запятую на точку для корректного формата
                            val normalizedBalance = editBalanceText.replace(",", ".")
                            viewModel.updateAccountBalance(normalizedBalance)
                        }
                        showEditBalanceDialog = false
                    }
                ) {
                    Text("Подтвердить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditBalanceDialog = false }
                ) {
                    Text("Отменить")
                }
            }
        )
    }
}

@Composable
fun AccountScreenContent(
    uiState: AccountScreenState,
    onCurrencyClick: () -> Unit = {},
    onEditNameClick: (String) -> Unit = {},
    onEditBalanceClick: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
            is AccountScreenState.Error -> {
                MyErrorBox(
                    message = uiState.message
                )
            }
            AccountScreenState.Loading -> {
                MyLoadingIndicator()
            }
            is AccountScreenState.Loaded -> {
                MyTopAppBar(
                    text = "${uiState.data.name}",
                    trailingIcon = R.drawable.edit,
                    onTrailingIconClick = {
                        onEditNameClick(uiState.data.name)
                    }
                )
                MyListItemWithLeadIcon(
                    modifier = Modifier
                        .height(56.dp)
                        .background(GreenLight),
                    icon = "\uD83D\uDCB0",
                    iconBg = Color.White,
                    content = {
                        Text(
                            text = "Баланс"
                        )
                    },
                    trailContent = {
                        Text(
                            text = "${uiState.data.balance} ${uiState.data.currency}"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onEditBalanceClick(uiState.data.balance)
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
                            text = "${uiState.data.currency}"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                    onClick = onCurrencyClick
                )
            }
        }
    }
}

@Composable
fun CurrencySelectionBottomSheet(
    onCurrencySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Выберите валюту",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        val currencies = listOf(
            "RUB" to "Российский рубль",
            "USD" to "Доллар",
            "EUR" to "Евро"
        )

        currencies.forEach { (code, name) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onCurrencySelected(code) }
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = code,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            if (currencies.last() != (code to name)) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}