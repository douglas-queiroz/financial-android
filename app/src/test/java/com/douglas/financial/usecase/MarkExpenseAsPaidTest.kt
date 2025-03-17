package com.douglas.financial.usecase

import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.util.DateUtil
import com.douglas.financial.util.UuidUtil
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.UUID

class MarkExpenseAsPaidTest {

    private val expenseDao = mockk<ExpenseDao>(relaxed = true)
    private val expensePaymentDao = mockk<ExpensePaymentDao>(relaxed = true)
    private val dateUtil = mockk<DateUtil>(relaxed = true)
    private val uuidUtil = mockk<UuidUtil>(relaxed = true)

    private lateinit var target: MarkExpenseAsPaid

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        target = MarkExpenseAsPaid(expenseDao, expensePaymentDao, dateUtil, uuidUtil)
    }

    @Test
    fun `When MarkExpenseAsPaid is called, an Expense is retrieved`() = runBlocking {
        val expenseId = "abc"
        target(expenseId = expenseId)
        verify { expenseDao.findById(expenseId) }
    }

    @Test
    fun `When MarkExpenseAsPaid is called, a ExpensePayment is inserted`() = runBlocking {
        val expenseId = "abc"
        val expenseValue = 100.0
        val date = LocalDateTime.now()
        val expense = mockk<Expense>(relaxed = true)
        val uuid = UUID.randomUUID()

        every { expense.value } returns expenseValue
        every { expense.id } returns expenseId
        every { dateUtil.getDateTimeNow() } returns date
        coEvery { expenseDao.findById(expenseId) } returns expense
        every { uuidUtil.generateUUID() } returns uuid

        target(expenseId = expenseId)

        verify { expensePaymentDao.insert(ExpensePayment(
            id = uuid.toString(),
            date = date,
            value = expenseValue,
            expenseId = expenseId
        )) }
    }
}