package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class CurrencyExchangeRateTrack(
    @PrimaryKey
    val currencyIdFrom: String,
    val currencyIdTo: String,
    val exchangeRate: Double,
    val date: LocalDateTime
)
