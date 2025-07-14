package com.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.theme.GreenLight

/**
 * Строка при нажатии на которую вызывается окошко выбора
 */
@Composable
fun MyPickerRow (
    modifier: Modifier = Modifier
        .height(56.dp)
        .background(GreenLight),
    trailingText: String,
    leadingText: String,
    onClick: () -> Unit,
) {
    MyListItemOnlyText(
        modifier = modifier,
        content = {
            Text(
                text = leadingText
            )
        },
        trailContent = {
            Text(text = trailingText)
        },
        onClick = {
            onClick.invoke()
        }
    )
}