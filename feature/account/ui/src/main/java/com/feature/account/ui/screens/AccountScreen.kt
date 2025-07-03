package com.feature.account.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.ui.R
import com.core.ui.components.MyTopAppBar

@Composable
fun AccountScreen() {
    AccountScreenContent()
}

@Composable
fun AccountScreenContent() {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мой счёт",
            trailingIcon = R.drawable.edit,
            onTrailingIconClick = {}
        )
    }
}