package com.douglas.financial.feature.expense.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.model.Expense
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class EditExpenseViewModel(
    private val expenseDao: ExpenseDao,
    private val onDismiss: () -> Unit
): ViewModel() {

    private lateinit var expense: Expense
    private var _state = MutableStateFlow(EditExpenseContract.State())
    val state: StateFlow<EditExpenseContract.State> = _state.asStateFlow()

    fun onEvent(event: EditExpenseContract.Event) {
        when(event) {
            is EditExpenseContract.Event.OnNameChange -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }
            is EditExpenseContract.Event.OnValueChange -> {
                _state.update {
                    it.copy(value = event.value)
                }
            }
            is EditExpenseContract.Event.OnDueDateChange -> {
                _state.update {
                    it.copy(dueDate = event.dueDate)
                }
            }
            is EditExpenseContract.Event.Save -> save()
            is EditExpenseContract.Event.Cancel -> onDismiss()
        }
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

        _state.update {
            it.copy(
                name = expense.name,
                value = expense.value.toBRLCurrency().replace("R$", "").trim(),
                dueDate = expense.dueDate.toString()
            )
        }
    }

    private fun save() = viewModelScope.launch {
        val value = _state
            .value
            .value
            .replace(".", "")
            .replace(",", ".")
            .toDouble()

        expense = expense.copy(
            name = _state.value.name,
            value = value,
            dueDate = _state.value.dueDate.toInt()
        )

        expenseDao.insertAll(listOf(expense))

        onDismiss()
    }
}