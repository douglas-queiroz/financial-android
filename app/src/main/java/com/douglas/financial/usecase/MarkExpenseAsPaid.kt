package com.douglas.financial.usecase

import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.util.DateUtil
import com.douglas.financial.util.UuidUtil
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class MarkExpenseAsPaid(
    private val expenseDao: ExpenseDao,
    private val expensePaymentDao: ExpensePaymentDao,
    private val dateUtil: DateUtil,
    private val uuidUtil: UuidUtil
) {

    suspend operator fun invoke(expenseId: String) = withContext(IO) {
        val expense = expenseDao.findById(expenseId)
        val payment = ExpensePayment(
            id = uuidUtil.generateUUID().toString(),
            expenseId = expense.id,
            date = dateUtil.getDateTimeNow(),
            value = expense.value
        )

        expensePaymentDao.insert(payment)
    }
}