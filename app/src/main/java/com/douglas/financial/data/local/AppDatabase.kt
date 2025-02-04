package com.douglas.financial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.douglas.financial.model.Expense

@Database(entities = [Expense::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}