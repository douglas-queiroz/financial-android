package com.douglas.financial.feature.expense.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.usecase.MarkExpenseAsPaid
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExpenseViewModel(
    private val expenseDao: ExpenseDao,
    private val expensePaymentDao: ExpensePaymentDao,
    private val markExpenseAsPaid: MarkExpenseAsPaid
): ViewModel() {

    var state by mutableStateOf(ExpenseContract.State())

    init {
        viewModelScope.launch {
            combine(expenseDao.getAll(), expensePaymentDao.getPaymentOfCurrentMonth(), ::populate)
                .collect {
                state = state.copy(expenses = it)
            }
        }
    }

    fun onEvent(event: ExpenseContract.Events) {
        when(event) {
            is ExpenseContract.Events.OnDelete -> {
                state = state.copy(expenseToBeDeleted = event.expense)
            }
            is ExpenseContract.Events.OnMarkPaid -> {
                viewModelScope.launch { markExpenseAsPaid(event.expense.id) }
            }
            is ExpenseContract.Events.Sync -> sync()
            is ExpenseContract.Events.Edit -> {
                state = state.copy(showEditDialog = true, editExpenseId = event.expense.id)
            }
            is ExpenseContract.Events.OnDismiss -> {
                state = state.copy(showEditDialog = false, editExpenseId = null)
            }
            is ExpenseContract.Events.AddExpense -> {
                state = state.copy(showEditDialog = true, editExpenseId = null)
            }
            is ExpenseContract.Events.OnDeleteDialogClose -> {
                state = state.copy(expenseToBeDeleted = null)
            }
            is ExpenseContract.Events.OnDeleteExpenseConfirmed -> {
                viewModelScope.launch {
                    expenseDao.deleteByExpenseId(event.expense.id)
                    state = state.copy(expenseToBeDeleted = null)
                }
            }
            is ExpenseContract.Events.OnMarkUnpaid -> {
                viewModelScope.launch {
                    expensePaymentDao.deleteById(event.expense.paymentId!!)
                }
            }
        }
    }

    private fun sync() = viewModelScope.launch(exceptionHandler) {
        //
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    private fun populate(
        expenseList: List<Expense>,
        expensePayments: List<ExpensePayment>
    ): List<ExpenseItem> {

        state = state.copy(
            total = expenseList.sumOf { it.value }.toBRLCurrency()
        )

        return expenseList.sortedBy {
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
    }
}