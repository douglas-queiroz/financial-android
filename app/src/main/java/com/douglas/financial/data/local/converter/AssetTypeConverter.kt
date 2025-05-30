package com.douglas.financial.data.local.converter

import androidx.room.TypeConverter
import com.douglas.financial.model.AssetType

class AssetTypeConverter {

    @TypeConverter
    fun fromAssetType(assetType: AssetType): Int {
        return assetType.id
    }

    @TypeConverter
    fun toAssetType(value: Int): AssetType {
        return AssetType.entries.find { it.id == value }!!
    }
}