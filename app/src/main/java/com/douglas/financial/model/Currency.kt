package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String = "",
    val exchangeRate: Double,
    val code: String,
    val isBase: Boolean = false
)
