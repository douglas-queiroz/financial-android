package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.douglas.financial.data.remote.ExpensePayment
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensePaymentDao {

    @Insert
    fun insert(expensePayment: ExpensePayment)

    @Query("SELECT * FROM ExpensePayment WHERE STRFTIME('%Y-%m', date) = STRFTIME('%Y-%m', 'now')")
    fun getPaymentOfCurrentMonth(): Flow<List<ExpensePayment>>

}