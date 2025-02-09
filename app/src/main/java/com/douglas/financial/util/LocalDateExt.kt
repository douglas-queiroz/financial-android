package com.douglas.financial.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.format(format: String = "dd/MM/yyyy"): String {
    return DateTimeFormatter
        .ofPattern(format, Locale.getDefault())
        .format(this)
}