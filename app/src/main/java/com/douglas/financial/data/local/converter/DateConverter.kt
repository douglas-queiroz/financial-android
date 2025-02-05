package com.douglas.financial.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(timestamp: Long) = Date(timestamp)

}