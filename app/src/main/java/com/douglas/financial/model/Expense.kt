package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Expense(
    @SerializedName("_id")
    @PrimaryKey
    val id: String,
    val name: String,
    val value: Double = 0.0,
    val dueDate: Int
)