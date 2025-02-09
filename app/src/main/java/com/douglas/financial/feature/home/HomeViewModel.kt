package com.douglas.financial.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.usecase.DownloadExpensesUseCase
import com.douglas.financial.util.format
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

                showExpenses(expenses, payments)

                return@combine Pair(total, totalToBePaid)
            }.collect {
                _state.value = _state.value.copy(
                    totalExpenses = it.first.toBRLCurrency(),
                    totalExpensesToBePaid = it.second.toBRLCurrency()
                )
            }
        }
    }

    private fun showExpenses(
        expenses: List<Expense>,
        payments: List<ExpensePayment>
    ) {
        val now = LocalDate.now()

        _state.value = _state.value.copy(
            expensesToBePaid = expenses.filter { expense ->
                return@filter !payments.any { payment -> payment.expenseId == expense.id }
            }.sortedBy {
                it.dueDate
            }.map { expense ->
                val expenseDueDate = try {
                    LocalDate.of(now.year, now.month, expense.dueDate)
                } catch (ex: DateTimeException) {
                    LocalDate.of(now.year, now.month + 1, 1).minusDays(1)
                }
                HomeContract.ExpensesToBePaid(
                    id = expense.id,
                    description = expense.name,
                    date = expenseDueDate.format(),
                    value = expense.value.toBRLCurrency()
                )
            }
        )
    }

    fun onEvent(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.DownloadExpenses -> downloadExpenses()
            is HomeContract.Event.MarkExpenseAsPaid -> markExpenseAsPaid(event.expenseId)
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

    @OptIn(ExperimentalUuidApi::class)
    fun markExpenseAsPaid(expenseId: String) {
        viewModelScope.launch(IO) {
            val expense = expenseDao.findById(expenseId)
            val expensePayment = ExpensePayment(
                id = Uuid.random().toHexString(),
                expenseId = expense.id,
                value = expense.value,
                date = LocalDateTime.now()
            )
            expensePaymentDao.insert(expensePayment)
        }
    }

}