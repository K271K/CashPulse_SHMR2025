package com.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.theme.GreenLight

/**
 * Строка при нажатии на которую вызывается окошко выбора даты
 */
@Composable
fun MyDatePickerRow (
    trailingText: String,
    leadingText: String,
    onClick: () -> Unit,
) {
    MyListItemOnlyText(
        modifier = Modifier
            .height(56.dp)
            .background(GreenLight),
        content = {
            Text(
                text = trailingText
            )
        },
        trailContent = {
            Text(text = leadingText)
        },
        onClick = {
            onClick.invoke()
        }
    )
}