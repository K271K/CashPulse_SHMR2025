package com.feature.category.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.ui.R
import com.core.ui.components.MyTopAppBar

@Composable
fun CategoryScreen() {
    CategoryScreenContent()
}

@Composable
fun CategoryScreenContent() {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои статьи",
        )
    }
}