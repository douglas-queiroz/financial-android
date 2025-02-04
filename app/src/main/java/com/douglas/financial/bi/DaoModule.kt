package com.douglas.financial.bi

import android.app.Application
import androidx.room.Room
import com.douglas.financial.data.local.AppDatabase
import org.koin.dsl.module

fun daoModule(applicationContext: Application) = module {

   val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "financial"
    ).build()

    factory {
        db.expenseDao()
    }
}