package com.douglas.financial.data.local.converter

import androidx.room.TypeConverter
import com.douglas.financial.model.AssetType

class AssetTypeConverter {

    @TypeConverter
    fun fromAssetType(assetType: AssetType): String {
        return assetType.id.toString()
    }

    @TypeConverter
    fun toAssetType(value: Int): AssetType {
        return AssetType.entries.find { it.id == value }!!
    }
}