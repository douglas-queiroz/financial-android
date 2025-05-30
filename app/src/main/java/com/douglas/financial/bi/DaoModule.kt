package com.douglas.financial.bi

import android.app.Application
import androidx.room.Room
import com.douglas.financial.data.local.AppDatabase
import com.douglas.financial.data.local.Migration1To2
import org.koin.dsl.module

fun daoModule(applicationContext: Application) = module {

   val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "financial"
    ).addMigrations(Migration1To2).build()

    factory {
        db.expenseDao()
    }
    factory {
        db.expensePaymentDao()
    }
    factory {
        db.assetDao()
    }
    factory {
        db.assetTrackDao()
    }
    factory {
        db.currencyDao()
    }
    factory {
        db.currencyExchangeRateTrackDao()
    }
}