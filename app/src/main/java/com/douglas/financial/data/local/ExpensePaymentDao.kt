package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.douglas.financial.model.ExpensePayment
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensePaymentDao {

    @Insert
    fun insert(expensePayment: ExpensePayment)

    @Query("SELECT * FROM ExpensePayment")
    fun getAll(): Flow<List<ExpensePayment>>

    @Query("SELECT * FROM ExpensePayment WHERE STRFTIME('%Y-%m', date) = STRFTIME('%Y-%m', 'now')")
    fun getPaymentOfCurrentMonth(): Flow<List<ExpensePayment>>

    @Query("DELETE FROM ExpensePayment WHERE id = :paymentId")
    suspend fun deleteById(paymentId: String)
}