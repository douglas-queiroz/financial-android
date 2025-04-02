package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.douglas.financial.model.AssetTrack

@Dao
interface AssetTrackDao {

    @Insert
    suspend fun insert(assetTrack: AssetTrack)

    @Query("SELECT * FROM assettrack")
    suspend fun getAll(): List<AssetTrack>

    @Query("SELECT * FROM assettrack WHERE assetId = :assetId")
    suspend fun getByAssetId(assetId: String): List<AssetTrack>
}
