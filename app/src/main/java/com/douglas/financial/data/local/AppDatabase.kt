package com.douglas.financial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.douglas.financial.data.local.converter.DateConverter
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.model.Expense

@Database(entities = [Expense::class, ExpensePayment::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun expensePaymentDao(): ExpensePaymentDao
}