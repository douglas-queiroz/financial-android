package com.douglas.financial.feature.expense.list

data class ExpenseItem(
    val id: String,
    val name: String,
    val value: String,
    val dueDate: String,
    val paymentId: String?,
)