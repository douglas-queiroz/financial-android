package com.douglas.financial.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        AutoMigration(from = 1, to = 2)
    ],
    version = 3,
)
@TypeConverters(DateConverter::class)
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
            INSERT INTO Asset (id, name, code, value, qtd, type, currencyId) VALUES
                ('7d7b1c4a-4a35-4c94-8056-06647986b1e7', 'ADVISORSHARES VICE ETF', 'VICE', 31.2953, 35, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('f90f6b2c-6241-4d92-9c13-91b1631d88e2', 'COMMUNICAT SVS SLCT SEC SPDR ETF', 'XLC', 94.36, 17, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('e2fef0c7-7f99-4501-902f-d0274d8d4075', 'GLOBAL X MLP & ENERGY INFRASTRUC', 'MLPX', 61.33, 46, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('943d6b55-34fd-4ee7-87c8-50f6c3b1171e', 'GLOBAL X URANIUM ETF', 'URA', 24.45, 35, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('09b5c625-0838-4c63-8e41-5f8f2b3792d0', 'GOLDMAN SACHS PHYSICAL GOLD ETF', 'AAAU', 32.65, 215, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('65be1d77-3d3b-4451-8a26-c5f3b5b8ac2b', 'ISHARES CORE S&P SMALL CAP ETF', 'IJR', 99.84, 5, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('1d2cf8ee-4629-465e-9504-3d297c6341c1', 'ISHARES MSCI CHINA ETF', 'MCHI', 51.91, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('0bd2e84a-60de-48dc-9112-d9f020822308', 'ISHARES MSCI EAFE VALUE ETF', 'EFV', 60.52, 80, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('1c40b0d8-cb61-4e5a-8759-ec1c86dfd1d2', 'SELECT SECTOR INDUSTRIALSPDR ETF', 'XLI', 129.01, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('37ecab7b-1720-435c-879c-370cf812f25e', 'SELECT SECTOR UTI SELECTSPDR ETF', 'XLU', 78.21, 1, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('f210ef90-8cf7-48c2-99a5-c97cbf5285ca', 'SPDR S&P OIL & GAS EXPL & PROD ETF', 'XOP', 113.38, 7, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('6edcbf7e-cfd8-4f71-8773-2092e8a25a90', 'VANGUARD FTSE DEVELOPED MARKET', 'VEA', 52.43, 40, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('808fae4b-6d48-42aa-9f59-25b8b6e6180d', 'VANGUARD FTSE EMERGING MARKETS', 'VWO', 44.87, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('be68eaa7-5478-4641-816e-5e4657d4a60d', 'VANGUARD REAL ESTATE ETF', 'VNQ', 87.01, 77, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('65c3e2a2-8e02-4a1c-8855-32dbdf00af23', 'VANGUARD S&P 500 ETF', 'VOO', 506.11, 21, 5, '33f269006b4c4e7e9690d006de5640e4');
            INSERT INTO Asset (id, name, code, value, qtd, type, currencyId) VALUES
                ('7d7b1c4a-4a35-4c94-8056-06647986b1e7', 'ADVISORSHARES VICE ETF', 'VICE', 31.2953, 35, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('f90f6b2c-6241-4d92-9c13-91b1631d88e2', 'COMMUNICAT SVS SLCT SEC SPDR ETF', 'XLC', 94.36, 17, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('e2fef0c7-7f99-4501-902f-d0274d8d4075', 'GLOBAL X MLP & ENERGY INFRASTRUC', 'MLPX', 61.33, 46, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('943d6b55-34fd-4ee7-87c8-50f6c3b1171e', 'GLOBAL X URANIUM ETF', 'URA', 24.45, 35, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('09b5c625-0838-4c63-8e41-5f8f2b3792d0', 'GOLDMAN SACHS PHYSICAL GOLD ETF', 'AAAU', 32.65, 215, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('65be1d77-3d3b-4451-8a26-c5f3b5b8ac2b', 'ISHARES CORE S&P SMALL CAP ETF', 'IJR', 99.84, 5, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('1d2cf8ee-4629-465e-9504-3d297c6341c1', 'ISHARES MSCI CHINA ETF', 'MCHI', 51.91, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('0bd2e84a-60de-48dc-9112-d9f020822308', 'ISHARES MSCI EAFE VALUE ETF', 'EFV', 60.52, 80, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('1c40b0d8-cb61-4e5a-8759-ec1c86dfd1d2', 'SELECT SECTOR INDUSTRIALSPDR ETF', 'XLI', 129.01, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('37ecab7b-1720-435c-879c-370cf812f25e', 'SELECT SECTOR UTI SELECTSPDR ETF', 'XLU', 78.21, 1, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('f210ef90-8cf7-48c2-99a5-c97cbf5285ca', 'SPDR S&P OIL & GAS EXPL & PROD ETF', 'XOP', 113.38, 7, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('6edcbf7e-cfd8-4f71-8773-2092e8a25a90', 'VANGUARD FTSE DEVELOPED MARKET', 'VEA', 52.43, 40, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('808fae4b-6d48-42aa-9f59-25b8b6e6180d', 'VANGUARD FTSE EMERGING MARKETS', 'VWO', 44.87, 10, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('be68eaa7-5478-4641-816e-5e4657d4a60d', 'VANGUARD REAL ESTATE ETF', 'VNQ', 87.01, 77, 5, '33f269006b4c4e7e9690d006de5640e4'),
                ('65c3e2a2-8e02-4a1c-8855-32dbdf00af23', 'VANGUARD S&P 500 ETF', 'VOO', 506.11, 21, 5, '33f269006b4c4e7e9690d006de5640e4');
        """.trimIndent())
    }
}
