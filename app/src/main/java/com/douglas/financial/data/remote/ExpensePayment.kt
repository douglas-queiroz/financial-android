package com.douglas.financial.data.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ExpensePayment(
    @PrimaryKey
    val id: String,
    val date: Date,
    val value: Double,
    val expenseId: String,
)
