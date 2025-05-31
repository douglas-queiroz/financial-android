package com.douglas.financial.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.douglas.financial.data.local.converter.AssetTypeConverter
import com.douglas.financial.data.local.converter.DateConverter
import com.douglas.financial.model.Asset
import com.douglas.financial.model.AssetTrack
import com.douglas.financial.model.AssetTrade
import com.douglas.financial.model.Currency
import com.douglas.financial.model.CurrencyExchangeRateTrack
import com.douglas.financial.model.Expense
import com.douglas.financial.model.ExpensePayment

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
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 3, to = 4)
    ],
    version = 4,
)
@TypeConverters(DateConverter::class, AssetTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun expensePaymentDao(): ExpensePaymentDao
    abstract fun assetDao(): AssetDao
    abstract fun assetTrackDao(): AssetTrackDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyExchangeRateTrackDao(): CurrencyExchangeRateTrackDao
}

internal object Migration1To2: Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            """.trimIndent())
    }
}
