package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.douglas.financial.model.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): List<Expense>

    @Query("SELECT * FROM expense WHERE id = :id")
    fun getById(id: String): Expense

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expense: List<Expense>)
}