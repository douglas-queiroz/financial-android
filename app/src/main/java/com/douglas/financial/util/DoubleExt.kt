package com.douglas.financial.util

import java.text.NumberFormat
import java.util.Locale

fun Double.toBRLCurrency(): String {
    val locale = Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    return numberFormat.format(this)
}