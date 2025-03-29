package com.douglas.financial.feature.expense.edit

import app.cash.turbine.test
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.model.Expense
import com.douglas.financial.util.toBRLCurrency
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class EditExpenseViewModelTest {

    @MockK(relaxed = true)
    private lateinit var expenseDao: ExpenseDao

    @MockK(relaxed = true)
    private lateinit var onDismiss: () -> Unit

    private lateinit var target: EditExpenseViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        target = EditExpenseViewModel(
            expenseDao = expenseDao,
            onDismiss = onDismiss
        )
    }

    @Test
    fun `When expenseId is null then show an empty form`() = runTest {
        target.setExpense(null)

        target.state.test {
            val state = awaitItem()
            assertEquals("", state.name)
            assertEquals("", state.value)
            assertEquals("", state.dueDate)


            val state2 = awaitItem()
            assertEquals("", state2.name)
            assertEquals("0,00", state2.value)
            assertEquals("0", state2.dueDate)
        }
    }

    @Test
    fun `When set expenseId null then fulfill the form`() = runTest {
        val expenseId = "expenseId"
        val expense = mockk<Expense>()
        val name = "name"
        val value = 10.0
        val dueDate = 10
        every { expense.name } returns name
        every { expense.value } returns value
        every { expense.dueDate } returns dueDate
        coEvery { expenseDao.getById(expenseId) } returns expense

        target.setExpense(expenseId)

        target.state.test {
            val state = awaitItem()
            assertEquals("", state.name)
            assertEquals("", state.value)
            assertEquals("", state.dueDate)


            val state2 = awaitItem()
            assertEquals(name, state2.name)
            assertEquals("10,00", state2.value)
            assertEquals(dueDate.toString(), state2.dueDate)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `When name change THEN update name`() = runTest {
        val name = "name"

        target.onEvent(EditExpenseContract.Event.OnNameChange(name))

        target.state.test {
            assertEquals(name, awaitItem().name)
        }
    }

    @Test
    fun `When value change THEN update value`() = runTest {
        val value = "10,00"
        target.onEvent(EditExpenseContract.Event.OnValueChange(value))

        target.state.test {
            assertEquals(value, awaitItem().value)
        }
    }

    @Test
    fun `When dueDate change THEN update dueDate`() = runTest {
        val dueDate = "10"
        target.onEvent(EditExpenseContract.Event.OnDueDateChange(dueDate))

        target.state.test {
            assertEquals(dueDate, awaitItem().dueDate)
        }
    }

    @Test
    fun `When save with expense Id THEN save with expense id`() = runTest {

        val expense = Expense(
            id = "expenseId",
            name = "name",
            value = 10.0,
            dueDate = 12
        )
        coEvery { expenseDao.getById(expense.id) } returns expense

        target.setExpense(expenseId = expense.id)
        target.onEvent(EditExpenseContract.Event.Save)

        coEvery { expenseDao.insertAll(listOf(expense)) }
    }
}