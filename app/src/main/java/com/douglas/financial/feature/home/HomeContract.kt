package com.douglas.financial.feature.home

class HomeContract {
    data class State(
        val totalExpensesToBePaid: String = "",
        val totalExpenses: String = "",
        val expensesToBePaid: List<ExpenseToBePaid> = emptyList()
    )

    data class ExpenseToBePaid(
        val id: String,
        val description: String,
        val date: String,
        val value: String,
    )

    sealed class Event {
        data object DownloadExpenses : Event()
        data object AddCurrencies : Event()
        data class MarkExpenseAsPaid(val expenseId: String) : Event()
    }
}