package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.douglas.financial.model.Asset

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset")
    suspend fun getAll(): List<Asset>

    @Query("SELECT * FROM asset WHERE id = :id")
    suspend fun getById(id: String): Asset

    @Insert
    suspend fun insert(asset: Asset)

    @Update
    suspend fun update(asset: Asset)
}
