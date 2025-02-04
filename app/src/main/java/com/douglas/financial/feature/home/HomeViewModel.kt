package com.douglas.financial.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.usecase.DownloadExpensesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val downloadExpensesUseCase: DownloadExpensesUseCase
): ViewModel() {

    fun onEvent(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.DownloadExpenses -> downloadExpenses()
        }
    }

    fun downloadExpenses() {
        viewModelScope.launch {
            downloadExpensesUseCase()
        }
    }
}