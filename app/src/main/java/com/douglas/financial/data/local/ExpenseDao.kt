package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id = :id")
    fun getById(id: String): Expense

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expense: List<Expense>)

    @Query("Select * from Expense " +
            "LEFT JOIN ExpensePayment ON Expense.id = ExpensePayment.expenseId " +
            "WHERE STRFTIME('%Y-%m', ExpensePayment.date) = STRFTIME('%Y-%m', 'now')"
    )
    fun getExpensesWithPayment(): Flow<Map<Expense, List<ExpensePayment>>>

    @Query("SELECT * FROM expense WHERE id = :expenseId")
    fun findById(expenseId: String): Expense
}