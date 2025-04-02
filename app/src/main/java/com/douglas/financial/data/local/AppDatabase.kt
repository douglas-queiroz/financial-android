package com.douglas.financial.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.douglas.financial.data.local.converter.DateConverter
import com.douglas.financial.model.Asset
import com.douglas.financial.model.AssetTrack
import com.douglas.financial.model.AssetTrade
import com.douglas.financial.model.Currency
import com.douglas.financial.model.CurrencyExchangeRateTrack
import com.douglas.financial.model.ExpensePayment
import com.douglas.financial.model.Expense

@Database(
    entities = [
        Expense::class,
        ExpensePayment::class,
        Asset::class,
        AssetTrack::class,
        AssetTrade::class,
        Currency::class,
        CurrencyExchangeRateTrack::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    version = 2,
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun expensePaymentDao(): ExpensePaymentDao
    abstract fun assetDao(): AssetDao
    abstract fun assetTrackDao(): AssetTrackDao
    abstract fun assetTradeDao(): AssetTradeDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyExchangeRateTrackDao(): CurrencyExchangeRateTrackDao
}