package com.douglas.financial.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ExpensePayment(
    @PrimaryKey
    val id: String,
    val date: LocalDateTime,
    val value: Double,
    val expenseId: String,
)
