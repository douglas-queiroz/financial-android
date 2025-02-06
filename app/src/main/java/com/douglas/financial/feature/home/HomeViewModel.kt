package com.douglas.financial.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.data.remote.ExpensePayment
import com.douglas.financial.model.Expense
import com.douglas.financial.usecase.DownloadExpensesUseCase
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    private val downloadExpensesUseCase: DownloadExpensesUseCase,
    private val expenseDao: ExpenseDao,
    private val expensePaymentDao: ExpensePaymentDao
): ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(expenseDao.getAll(), expensePaymentDao.getPaymentOfCurrentMonth()) { expenses, payments ->
                val total = calculateTotal(expenses)
                val totalToBePaid = calculateTotalToBePaid(total, payments)

                return@combine Pair(total, totalToBePaid)
            }.collect {
                _state.value = _state.value.copy(
                    totalExpenses = it.first.toBRLCurrency(),
                    totalExpensesToBePaid = it.second.toBRLCurrency()
                )
            }
        }
    }

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

    fun calculateTotalToBePaid(total: Double, payments: List<ExpensePayment>): Double {
        val totalPayment = payments.sumOf { it.value }

        return total - totalPayment
    }

    fun calculateTotal(expenses: List<Expense>) =  expenses.sumOf { expense -> expense.value }
}