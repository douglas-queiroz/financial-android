package com.douglas.financial.feature.home

class HomeContract {
    data class State(
        val totalExpensesToBePaid: String = "",
        val totalExpenses: String = "",
    )

    sealed class Event {
        data object DownloadExpenses : Event()
    }
}