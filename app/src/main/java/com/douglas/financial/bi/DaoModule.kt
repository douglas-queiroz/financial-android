package com.douglas.financial.bi

import android.app.Application
import androidx.room.Room
import com.douglas.financial.data.local.AppDatabase
import com.douglas.financial.data.local.Migration2To3
import org.koin.dsl.module

fun daoModule(applicationContext: Application) = module {

   val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "financial"
    ).addMigrations(Migration2To3).build()

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
        db.assetTradeDao()
    }
    factory {
        db.currencyDao()
    }
    factory {
        db.currencyExchangeRateTrackDao()
    }
}