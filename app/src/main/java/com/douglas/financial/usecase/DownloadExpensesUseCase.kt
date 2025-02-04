package com.douglas.financial.usecase

import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.remote.ExpenseApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class DownloadExpensesUseCase(
    private val expenseApi: ExpenseApi,
    private val expenseDao: ExpenseDao
) {

    suspend operator fun invoke() {
        withContext(IO) {
            val expenses = expenseApi.getAll()
            expenseDao.insertAll(expenses)
        }
    }
}