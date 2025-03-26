package com.douglas.financial.feature.expense.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.usecase.MarkExpenseAsPaid
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val expenseDao: ExpenseDao,
    private val expensePaymentDao: ExpensePaymentDao,
    private val markExpenseAsPaid: MarkExpenseAsPaid
): ViewModel() {

    private var _state = MutableStateFlow(ExpenseContract.State())
    val state: StateFlow<ExpenseContract.State> = _state.onStart {
        loadExpenses()
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = _state.value
    )

    fun onEvent(event: ExpenseContract.Events) {
        when(event) {
            is ExpenseContract.Events.OnDelete -> {
                _state.update { it.copy(expenseToBeDeleted = event.expense) }
            }
            is ExpenseContract.Events.OnMarkPaid -> {
                viewModelScope.launch { markExpenseAsPaid(event.expense.id) }
            }
            is ExpenseContract.Events.Edit -> {
                _state.update { it.copy(showEditDialog = true, editExpenseId = event.expense.id) }
            }
            is ExpenseContract.Events.OnDismiss -> {
                _state.update { it.copy(showEditDialog = true, editExpenseId = null) }
            }
            is ExpenseContract.Events.AddExpense -> {
                _state.update { it.copy(showEditDialog = true, editExpenseId = null) }
            }
            is ExpenseContract.Events.OnDeleteDialogClose -> {
                _state.update { it.copy(expenseToBeDeleted = null) }
            }
            is ExpenseContract.Events.OnDeleteExpenseConfirmed -> {
                viewModelScope.launch {
                    expenseDao.deleteByExpenseId(event.expense.id)
                    _state.update { it.copy(expenseToBeDeleted = null) }
                }
            }
            is ExpenseContract.Events.OnMarkUnpaid -> {
                viewModelScope.launch {
                    expensePaymentDao.deleteById(event.expense.paymentId!!)
                }
            }
        }
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            combine(
                expenseDao.getAll(),
                expensePaymentDao.getPaymentOfCurrentMonth(),
            ) { expenseList, expensePayments ->
                val expenses = expenseList.sortedBy {
                    it.dueDate
                }.map { expense ->
                    ExpenseItem(
                        id = expense.id,
                        name = expense.name,
                        value = expense.value.toBRLCurrency(),
                        dueDate = expense.dueDate.toString(),
                        paymentId = expensePayments.find { payment -> payment.expenseId == expense.id }?.id
                    )
                }

                return@combine ExpenseContract.State(
                    total = expenseList.sumOf { it.value }.toBRLCurrency(),
                    expenses = expenses
                )
            }.collect { newState ->
                _state.update {
                    it.copy(
                        total = newState.total,
                        expenses = newState.expenses
                    )
                }
            }
        }
    }
}