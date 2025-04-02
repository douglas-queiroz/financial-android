package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class AssetTrack(
    @PrimaryKey
    val id: String,
    val assetId: String,
    val date: LocalDateTime,
    val value: Double
)
