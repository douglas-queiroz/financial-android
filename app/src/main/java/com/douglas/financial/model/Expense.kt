package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Expense(
    @SerialName("_id")
    @PrimaryKey
    val id: String,
    val name: String,
    val value: Double,
    val dueDate: Int
)