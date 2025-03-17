package com.douglas.financial.feature.expense.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.model.Expense
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class EditExpenseViewModel(
    private val expenseDao: ExpenseDao,
    private val onDismiss: () -> Unit
): ViewModel() {

    private lateinit var expense: Expense
    var state by mutableStateOf(EditExpenseContract.State())

    fun onEvent(event: EditExpenseContract.Event) {
        when(event) {
            is EditExpenseContract.Event.OnNameChange -> { state = state.copy(name = event.name) }
            is EditExpenseContract.Event.OnValueChange -> {
                state = state.copy(value = event.value)
            }
            is EditExpenseContract.Event.OnDueDateChange -> {
                state = state.copy(dueDate = event.dueDate)
            }
            is EditExpenseContract.Event.Save -> save()
            is EditExpenseContract.Event.Cancel -> onDismiss()
        }
        println("state = ${state.name}")
        println("state = ${state.value}")
    }

    @OptIn(ExperimentalUuidApi::class)
    fun setExpense(expenseId: String?) = viewModelScope.launch {
        expense = if (expenseId == null) {
            Expense(
                id = Uuid.random().toString(),
                name = "",
                value = 0.0,
                dueDate = 0
            )
        } else {
            expenseDao.getById(expenseId)
        }

        state = state.copy(
            name = expense.name,
            value = expense.value.toBRLCurrency().replace("R$", "").trim(),
            dueDate = expense.dueDate.toString()
        )
    }

    private fun save() = viewModelScope.launch {
        val value = state.value.replace(".", "").replace(",", ".").toDouble()

        expense = expense.copy(
            name = state.name,
            value = value,
            dueDate = state.dueDate.toInt()
        )

        expenseDao.insertAll(listOf(expense))

        onDismiss()
    }
}