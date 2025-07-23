package com.douglas.financial.feature.home

import app.cash.turbine.test
import com.douglas.financial.data.local.AssetDao
import com.douglas.financial.data.local.CurrencyDao
import com.douglas.financial.data.local.ExpenseDao
import com.douglas.financial.data.local.ExpensePaymentDao
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.usecase.AddCurrenciesUseCase
import com.douglas.financial.usecase.MarkExpenseAsPaid
import com.douglas.financial.usecase.UpdateAssetsValueUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class HomeViewModelTest {

    @MockK(relaxed = true)
    private lateinit var updateAssetsValueUseCase: UpdateAssetsValueUseCase

    @MockK(relaxed = true)
    private lateinit var expenseDao: ExpenseDao

    @MockK(relaxed = true)
    private lateinit var expensePaymentDao: ExpensePaymentDao

    @MockK(relaxed = true)
    private lateinit var markExpenseAsPaid: MarkExpenseAsPaid

    @MockK(relaxed = true)
    private lateinit var addCurrenciesUseCase: AddCurrenciesUseCase

    @MockK(relaxed = true)
    private lateinit var assetDao: AssetDao

    @MockK(relaxed = true)
    private lateinit var currencyDao: CurrencyDao

    private lateinit var target: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        target = HomeViewModel(
            expenseDao = expenseDao,
            expensePaymentDao = expensePaymentDao,
            updateAssetsValueUseCase = updateAssetsValueUseCase,
            markExpenseAsPaid = markExpenseAsPaid,
            addCurrenciesUseCase = addCurrenciesUseCase,
            assetDao = assetDao,
            currencyDao = currencyDao
        )
    }

//    @Test
//    fun `When a 10 value expense THEN total expenses is R$ 10,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense("id", "teste", 10.0, 1))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(emptyList())
//        every { assetDao.getAllFlow() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals("", awaitItem().totalExpenses)
//            assertEquals("R$ 10,00", awaitItem().totalExpenses)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When a 10 value expense and a 5 value payment THEN total expenses is R$ 10,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense("id", "teste", 10.0, 1))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(
//            listOf(ExpensePayment("idpayment", LocalDateTime.now(), 5.0, "id"))
//        )
//        every { assetDao.getAllFlow() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals("", awaitItem().totalExpenses)
//            assertEquals("R$ 10,00", awaitItem().totalExpenses)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When a 10 value expense and a 5 value payment THEN total to be paid is R$ 5,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense("id", "teste", 10.0, 1))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(
//            listOf(ExpensePayment("idpayment", LocalDateTime.now(), 5.0, "id"))
//        )
//        every { assetDao.getAllFlow() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals("", awaitItem().totalExpensesToBePaid)
//            assertEquals("R$ 5,00", awaitItem().totalExpensesToBePaid)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When a 10 value expense has no payment THEN show a expense with R$ 10,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense("id", "teste", 10.0, 1))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(
//            emptyList()
//        )
//        every { assetDao.getAllFlow() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals(emptyList<HomeContract.ExpenseToBePaid>(), awaitItem().expensesToBePaid)
//            assertEquals("R$ 10,00", awaitItem().expensesToBePaid[0].value)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When a 10 value expense has payment THEN don't show a expense with R$ 10,00`() = runTest {
//        every { expenseDao.getAll() } returns flowOf(
//            listOf(Expense("id", "teste", 10.0, 1))
//        )
//        every { expensePaymentDao.getPaymentOfCurrentMonth() } returns flowOf(
//            listOf(ExpensePayment("idpayment", LocalDateTime.now(), 10.0, "id"))
//        )
//        every { assetDao.getAllFlow() } returns flowOf(emptyList())
//
//        target.state.test {
//            assertEquals(emptyList<HomeContract.ExpenseToBePaid>(), awaitItem().expensesToBePaid)
//            assertEquals(emptyList<HomeContract.ExpenseToBePaid>(), awaitItem().expensesToBePaid)
//            cancelAndConsumeRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `When tap download expenses button THEN call download expenses use case`() = runTest {
//        target.onEvent(HomeContract.Event.DownloadPriceUpdate)
//
//        coVerify {
//            updateAssetsValueUseCase()
//        }
//    }
//
//    @Test
//    fun `When tap paid button THEN call mark expenses as paid use case`() = runTest {
//        val id = "id"
//
//        target.onEvent(HomeContract.Event.MarkExpenseAsPaid(id))
//
//        coVerify {
//            markExpenseAsPaid(id)
//        }
//    }
}