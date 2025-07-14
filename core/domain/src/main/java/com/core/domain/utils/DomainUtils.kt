package com.core.domain.utils

fun formatDateToISO8061(date: String, time: String): String{
    val formattedDate = java.time.LocalDateTime.parse(
        "${date}T${time.trim()}:00", // Удаляем пробелы с помощью trim()
        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss")
    ).atZone(java.time.ZoneId.of("UTC")) // Указываем зону UTC
        .format(java.time.format.DateTimeFormatter.ISO_INSTANT) // Форматируем с Z
    return formattedDate
}