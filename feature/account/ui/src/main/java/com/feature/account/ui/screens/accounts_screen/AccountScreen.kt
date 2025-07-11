package com.feature.account.ui.screens.accounts_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyLoadingIndicator
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight

@Composable
fun AccountScreen(
    viewModelFactory: AccountViewModelFactory,
    onEditAccountDataClick: (id: Int) -> Unit
) {
    val viewModel: AccountsViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.accountScreenState.collectAsStateWithLifecycle()

    AccountScreenContent(
        uiState = uiState,
        onEditAccountDataClick = onEditAccountDataClick
    )
}

@Composable
fun AccountScreenContent(
    uiState: AccountScreenState,
    onEditAccountDataClick: (id: Int) -> Unit,
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
                    text = "Мой счёт",
                    trailingIcon = R.drawable.edit,
                    onTrailingIconClick = {
                        onEditAccountDataClick(uiState.data.accounts.filter { it.isSelected }
                            .first().id)
                    }
                )
                LazyColumn {
                    items(uiState.data.accounts) {
                        MyListItemOnlyText(
                            modifier = Modifier
                                .height(56.dp)
                                .background(if (it.isSelected) GreenLight else MaterialTheme.colorScheme.background),
                            content = {
                                Text(
                                    text = it.name
                                )
                            },
                            trailContent = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            },
                            onClick = {

                            }
                        )
                        HorizontalDivider()
                    }
                }
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