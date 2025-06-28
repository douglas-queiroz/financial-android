package com.douglas.financial.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class CurrencyExchangeRateTrack(
    @PrimaryKey
    @ColumnInfo(defaultValue = "1")
    val id: String,
    val currencyIdFrom: String,
    val currencyIdTo: String,
    val exchangeRate: Double,
    val date: LocalDateTime
)
