package com.core.domain.utils

import java.time.Instant
import java.time.LocalDateTime

fun formatDateToISO8061(date: String, time: String): String{
    println("formatDateToISO8061 input: $date $time")
    val formattedDate = LocalDateTime.parse(
        "${date}T${time.trim()}:00", // Удаляем пробелы с помощью trim()
        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss")
    ).atZone(java.time.ZoneId.systemDefault()) // Используем локальную зону пользователя
        .withZoneSameInstant(java.time.ZoneId.of("UTC")) // Переводим в UTC
        .format(java.time.format.DateTimeFormatter.ISO_INSTANT) // Форматируем с Z
    println("formattedDate: $formattedDate")
    return formattedDate
}

fun formatISO8601ToDate(iso8601: String): String {
    val instant = Instant.parse(iso8601)
    val localDateTime = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
    return localDateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

fun formatISO8601ToTime(iso8601: String): String {
    val instant = Instant.parse(iso8601)
    val localDateTime = instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
    return localDateTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
}