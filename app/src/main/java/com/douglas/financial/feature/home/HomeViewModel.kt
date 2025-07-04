package com.douglas.financial.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.douglas.financial.data.local.AssetDao
import com.douglas.financial.data.local.CurrencyDao
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Asset
import com.douglas.financial.model.Currency
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.usecase.AddCurrenciesUseCase
import com.douglas.financial.usecase.MarkExpenseAsPaid
import com.douglas.financial.usecase.UpdateAssetsValueUseCase
import com.douglas.financial.util.format
import com.douglas.financial.util.toBRLCurrency
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DateTimeException
import java.time.LocalDate

class HomeViewModel(
    private val expenseDao: ExpenseDao,
    private val expensePaymentDao: ExpensePaymentDao,
    private val updateAssetsValueUseCase: UpdateAssetsValueUseCase,
    private val markExpenseAsPaid: MarkExpenseAsPaid,
    private val addCurrenciesUseCase: AddCurrenciesUseCase,
    private val assetDao: AssetDao,
    private val currencyDao: CurrencyDao
): ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.onStart {
        loadExpenses()
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = _state.value
    )

    fun onEvent(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.DownloadPriceUpdate -> updateAssetsValues()
            is HomeContract.Event.AddCurrencies -> viewModelScope.launch { addCurrenciesUseCase() }
            is HomeContract.Event.MarkExpenseAsPaid -> markAsPaid(event.expenseId)
        }
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            combine(
                expenseDao.getAll(),
                expensePaymentDao.getPaymentOfCurrentMonth(),
                assetDao.getAllFlow()
            ) { expenses, payments, assets ->
                val totalAssets = calculateTotalAssets(assets)
                val totalExpenses = calculateTotal(expenses)
                val totalToBePaid = calculateTotalToBePaid(totalExpenses, payments)
                val expensesListToBePaid = createExpensesToBePaid(expenses, payments)

                HomeContract.State(
                    totalAssets = totalAssets.toBRLCurrency(),
                    totalExpenses = totalExpenses.toBRLCurrency(),
                    totalExpensesToBePaid = totalToBePaid.toBRLCurrency(),
                    expensesToBePaid = expensesListToBePaid
                )
            }.collect { newState ->
                _state.update {
                    it.copy(
                        totalAssets = newState.totalAssets,
                        totalExpenses = newState.totalExpenses,
                        totalExpensesToBePaid = newState.totalExpensesToBePaid,
                        expensesToBePaid = newState.expensesToBePaid
                    )
                }
            }
        }
    }

    private suspend fun calculateTotalAssets(assets: List<Asset>): Double {
        val currencies: List<Currency>
        withContext(IO) {
             currencies = currencyDao.getAll()
        }

        return currencies.sumOf { currency ->
            val assetsByCurrency = assets.filter { it.currencyId == currency.id }
            val totalByCurrency = assetsByCurrency.sumOf { it.value * it.qtd }
            if (currency.isBase) {
                totalByCurrency
            } else {
                totalByCurrency * currency.exchangeRate
            }
        }
    }

    private fun createExpensesToBePaid(
        expenses: List<Expense>,
        payments: List<ExpensePayment>
    ): List<HomeContract.ExpenseToBePaid> {
        val now = LocalDate.now()

        return expenses.filter { expense ->
            return@filter !payments.any { payment -> payment.expenseId == expense.id }
        }.sortedBy {
            it.dueDate
        }.take(3)
        .map { expense ->
            val expenseDueDate = try {
                LocalDate.of(now.year, now.month, expense.dueDate)
            } catch (_: DateTimeException) {
                LocalDate.of(now.year, now.month + 1, 1).minusDays(1)
            }
            HomeContract.ExpenseToBePaid(
                id = expense.id,
                description = expense.name,
                date = expenseDueDate.format(),
                value = expense.value.toBRLCurrency()
            )
        }
    }

    private fun updateAssetsValues() = viewModelScope.launch {
        updateAssetsValueUseCase()
    }

    private fun markAsPaid(expenseId: String) = viewModelScope.launch {
        markExpenseAsPaid(expenseId)
    }

    private fun calculateTotalToBePaid(total: Double, payments: List<ExpensePayment>): Double {
        val totalPayment = payments.sumOf { it.value }
        return total - totalPayment
    }

    private fun calculateTotal(expenses: List<Expense>) =  expenses.sumOf {
        expense -> expense.value
    }
}