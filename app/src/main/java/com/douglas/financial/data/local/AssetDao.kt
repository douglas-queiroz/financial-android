package com.douglas.financial.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.douglas.financial.model.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Query("SELECT * FROM asset")
    fun getAllFlow(): Flow<List<Asset>>

    @Query("SELECT * FROM asset")
    fun getAll(): List<Asset>

    @Query("SELECT * FROM asset WHERE id = :id")
    suspend fun getById(id: String): Asset

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(asset: Asset)

    @Update
    suspend fun update(asset: Asset)

    @Delete
    suspend fun delete(asset: Asset)
}
