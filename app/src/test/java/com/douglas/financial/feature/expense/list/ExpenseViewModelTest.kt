package com.douglas.financial.feature.expense.list

import app.cash.turbine.test
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.usecase.MarkExpenseAsPaid
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDateTime
import java.util.Locale
import java.util.TimeZone

class ExpenseViewModelTest {

    @MockK(relaxed = true)
    private lateinit var expenseDao: ExpenseDao

    @MockK(relaxed = true)
    private lateinit var expensePaymentDao: ExpensePaymentDao

    @MockK(relaxed = true)
    private lateinit var markExpenseAsPaid: MarkExpenseAsPaid

    private lateinit var target: ExpenseViewModel

    @Before
    fun setUp() {
        Locale.setDefault(Locale.US)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        MockKAnnotations.init(this)
        target = ExpenseViewModel(
            expenseDao = expenseDao,
            expensePaymentDao = expensePaymentDao,
            markExpenseAsPaid = markExpenseAsPaid
        )
    }

//    @Test
//    fun `When load a expense with name ABC THEN show a expense with name ABC`() = runTest {
//        val expenseName = "ABC"
//        every { expenseDao.getAll() } returns flowOf(listOf(Expense(
//            id = "id",
//            name = expenseName,
//            value = 10.0,
//            dueDate = 1
//        )))
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals(emptyList<ExpenseItem>(), awaitItem().expenses)
//            assertEquals(expenseName, awaitItem().expenses[0].name)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When load a expense with value 10 THEN show a expense with name R$ 10,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(listOf(Expense(
//            id = "id",
//            name = "",
//            value = 10.0,
//            dueDate = 1
//        )))
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals(emptyList<ExpenseItem>(), awaitItem().expenses)
//            assertEquals("R$ 10,00", awaitItem().expenses[0].value)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When load a expense with dueDate 10 THEN show a expense with dueDate 10`() = runTest {
//        val expenseDueDate = 10
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense(
//                id = "id",
//                name = "test",
//                value = 10.0,
//                dueDate = expenseDueDate
//            ))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(emptyList())
//
//        target.state.test {
//            assert(awaitItem().expenses.isEmpty())
//            assertEquals("10", awaitItem().expenses[0].dueDate)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When load a expense with no payment THEN the shown expense has paymentId null`() = runTest {
//        val expenseDueDate = 10
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense(
//                id = "id",
//                name = "test",
//                value = 10.0,
//                dueDate = expenseDueDate
//            ))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(emptyList())
//
//        target.state.test {
//            var actual = awaitItem()
//            println("Actual1: '$actual'")
//            assertEquals(emptyList<ExpenseItem>(), actual.expenses)
//
//            actual = awaitItem()
//            println("Actual2: '$actual'")
//            assertEquals(null, actual.expenses[0].paymentId)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When load a expense with payment THEN the shown expense has paymentId`() = runTest {
//        val expenseId = "expenseId"
//        val paymentId = "paymentId"
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense(
//                id = expenseId,
//                name = "test",
//                value = 10.0,
//                dueDate = 10
//            ))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(
//            listOf(ExpensePayment(
//                id = paymentId,
//                date = LocalDateTime.now(),
//                value = 10.0,
//                expenseId = expenseId
//            ))
//        )
//
//        target.state.test {
//            assertEquals(emptyList<ExpenseItem>(), awaitItem().expenses)
//            assertEquals(paymentId, awaitItem().expenses[0].paymentId)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When tap paid button ADD THEN show a dialog`() = runTest {
//        target.onEvent(ExpenseContract.Events.AddExpense)
//
//
//        target.state.test {
//            assertEquals(false, awaitItem().showEditDialog)
//            assertEquals(true, awaitItem().showEditDialog)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When tap button Delete THEN show expense to be deleted`() = runTest {
//        val expenseItem = mockk<ExpenseItem>()
//        target.onEvent(ExpenseContract.Events.OnDelete(expenseItem))
//
//        target.state.test {
//            assertEquals(null, awaitItem().expenseToBeDeleted)
//            assertEquals(expenseItem, awaitItem().expenseToBeDeleted)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When tap button Edit THEN show edit dialog`() = runTest {
//        val expenseItem = mockk<ExpenseItem>()
//        val expenseId = "ExpenseId"
//        every { expenseItem.id } returns expenseId
//        target.onEvent(ExpenseContract.Events.Edit(expenseItem))
//
//        target.state.test {
//            val state1 = awaitItem()
//            assertEquals(false, state1.showEditDialog)
//            assertEquals(null, state1.editExpenseId)
//
//            val state2 = awaitItem()
//            assertEquals(true, state2.showEditDialog)
//            assertEquals(expenseId, state2.editExpenseId)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When OnDismiss THEN dismiss edit dialog`() = runTest {
//
//        target.onEvent(ExpenseContract.Events.OnDismissEditDialog)
//
//        target.state.test {
//            val state1 = awaitItem()
//            assertEquals(false, state1.showEditDialog)
//            assertEquals(null, state1.editExpenseId)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When AddExpense THEN show edit dialog with no expenseId`() = runTest {
//        target.onEvent(ExpenseContract.Events.AddExpense)
//
//        target.state.test {
//            val state1 = awaitItem()
//            assertEquals(false, state1.showEditDialog)
//            assertEquals(null, state1.editExpenseId)
//
//            val state2 = awaitItem()
//            assertEquals(true, state2.showEditDialog)
//            assertEquals(null, state2.editExpenseId)
//
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When OnDeleteDialogClose THEN dismiss deleted dialog`() = runTest {
//        target.onEvent(ExpenseContract.Events.OnDeleteDialogClose)
//
//        target.state.test {
//            assertEquals(null, awaitItem().expenseToBeDeleted)
//
//            cancelAndConsumeRemainingEvents()
//        }
//
//    }
//
//    @Test
//    fun `When OnMarkPaid THEN mark expense as paid`() = runTest {
//        val expenseItem = mockk<ExpenseItem>(relaxed = true)
//        target.onEvent(ExpenseContract.Events.OnMarkPaid(expenseItem))
//
//        coVerify {
//            markExpenseAsPaid(expenseItem.id)
//        }
//    }
//
//    @Test
//    fun `When OnDeleteExpenseConfirmed THEN delete expense`() = runTest {
//        val expenseItem = mockk<ExpenseItem>(relaxed = true)
//        target.onEvent(ExpenseContract.Events.OnDeleteExpenseConfirmed(expenseItem))
//
//        coVerify {
//            expenseDao.deleteByExpenseId(expenseItem.id)
//        }
//    }
//
//    @Test
//    fun `When OnDeleteExpenseConfirmed THEN dismiss delete dialog`() = runTest {
//        val expenseItem = mockk<ExpenseItem>(relaxed = true)
//        target.onEvent(ExpenseContract.Events.OnDeleteExpenseConfirmed(expenseItem))
//
//        target.state.test {
//            assertEquals(null, awaitItem().expenseToBeDeleted)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When OnMarkUnpaid THEN mark expense as unpaid`() = runTest {
//        val expenseItem = mockk<ExpenseItem>(relaxed = true)
//        target.onEvent(ExpenseContract.Events.OnMarkUnpaid(expenseItem))
//        coVerify {
//            expensePaymentDao.deleteById(expenseItem.paymentId!!)
//        }
//
//    }
}