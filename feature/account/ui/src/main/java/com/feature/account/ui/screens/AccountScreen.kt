package com.feature.account.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.core.domain.models.AccountDomainModel
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyTopAppBar

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    AccountScreenContent(
        uiState = uiState,
        expanded = expanded,
        onDropdownToggle = { expanded = it },
        onNameChanged = { name ->
            val current = (uiState as? AccountScreenState.Loaded)?.data?.toDomainAccount() ?: AccountDomainModel(
                balance = "0.0",
                currency = "RUB",
                id = 211,
                name = "",
                userId = 12,
                createdAt = null,
                updatedAt = null
            )
            viewModel.loadAccount(current.copy(name = name))
        },
        onCurrencySelected = { currency -> viewModel.updateAccount((uiState as? AccountScreenState.Loaded)?.data?.name ?: "", currency) },
        onSaveClick = { viewModel.updateAccount((uiState as? AccountScreenState.Loaded)?.data?.name ?: "", (uiState as? AccountScreenState.Loaded)?.data?.currency ?: "RUB") },
        onClearError = viewModel::clearError
    )
}

@Composable
fun AccountScreenContent(
    uiState: AccountScreenState,
    expanded: Boolean,
    onDropdownToggle: (Boolean) -> Unit,
    onNameChanged: (String) -> Unit,
    onCurrencySelected: (String) -> Unit,
    onSaveClick: () -> Unit,
    onClearError: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мой счёт",
            trailingIcon = R.drawable.edit,
            onTrailingIconClick = {}
        )
        when (uiState) {
            is AccountScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AccountScreenState.Error -> {
                MyErrorBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = uiState.message
                )
            }
            is AccountScreenState.Loaded -> {
                val data = uiState.data
                OutlinedTextField(
                    value = data.name,
                    onValueChange = onNameChanged,
                    label = { Text(text = "Название счёта") }
                )
                Text(text = "Валюта")
                Button(
                    onClick = {onDropdownToggle(true)}
                ) {
                    Text(text = data.currency)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onDropdownToggle(false) }
                ) {
                    DropdownMenuItem(
                        text = { Text("RUB") },
                        onClick = {
                            onCurrencySelected("RUB")
                            onDropdownToggle(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("USD") },
                        onClick = {
                            onCurrencySelected("USD")
                            onDropdownToggle(false)
                        }
                    )
                }
                Text("Баланс: ${data.balance}")
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Сохранить")
                }
            }
        }
    }
}