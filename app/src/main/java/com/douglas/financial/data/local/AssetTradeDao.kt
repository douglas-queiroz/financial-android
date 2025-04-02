package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.douglas.financial.model.AssetTrade

@Dao
interface AssetTradeDao {

    @Insert
    suspend fun insert(assetTrade: AssetTrade)

    @Query("SELECT * FROM assettrade")
    suspend fun getAll(): List<AssetTrade>
}
