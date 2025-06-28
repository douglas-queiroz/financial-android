package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.douglas.financial.model.Currency

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insertAll(currencyList: List<Currency>)

    @Update
    suspend fun update(currency: Currency)

    @Query("SELECT * FROM currency")
    suspend fun getAll(): List<Currency>

    @Query("SELECT * FROM currency WHERE isBase = 1 LIMIT 1")
    fun getBaseCurrency(): Currency?
}
