package com.feature.expenses.ui.models

import androidx.compose.runtime.Immutable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Immutable
data class ExpensesHistoryUiModel(
    val id: Int,
    val emojiData: String? = null,
    val name: String,
    val description: String? = null,
    val amount: String,
    val time: String,
    val currency: String
)

fun String.formatExpenseDate() : String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Учитываем UTC из 'Z'
        val date = inputFormat.parse(this) ?: Date()

        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("UTC") // Сохраняем в UTC
        outputFormat.format(date)
    } catch (_: Exception) {
        this
    }
}