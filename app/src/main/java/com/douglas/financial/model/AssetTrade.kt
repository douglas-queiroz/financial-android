package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class AssetTrade(
    @PrimaryKey
    val id: String,
    val assetId: String,
    val date: LocalDateTime,
    val qtd: Double,
    val value: Double,
    val total: String
)
