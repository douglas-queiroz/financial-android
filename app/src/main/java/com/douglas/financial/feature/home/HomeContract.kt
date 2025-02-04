package com.douglas.financial.feature.home

class HomeContract {

    sealed class Event {
        data object DownloadExpenses : Event()
    }
}