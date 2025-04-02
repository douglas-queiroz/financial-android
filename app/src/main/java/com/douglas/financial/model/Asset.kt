package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset (
    @PrimaryKey
    val id: String,
    val name: String,
    val value: Double,
    val qtd: Double,
    val type: AssetType,
    val currencyId: String,
)
