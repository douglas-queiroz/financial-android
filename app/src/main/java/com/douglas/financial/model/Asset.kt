package com.douglas.financial.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Asset (
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo(defaultValue = "")
    val code: String,
    val value: Double,
    val qtd: Double,
    val type: AssetType,
    val currencyId: String,
)
