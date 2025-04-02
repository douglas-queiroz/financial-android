package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.douglas.financial.model.CurrencyExchangeRateTrack

@Dao
interface CurrencyExchangeRateTrackDao {

    @Insert
    suspend fun insertAll(exchangeRateList: List<CurrencyExchangeRateTrack>)
}
