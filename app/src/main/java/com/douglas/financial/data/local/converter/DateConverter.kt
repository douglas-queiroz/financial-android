package com.douglas.financial.data.local.converter

import androidx.room.TypeConverter
import com.douglas.financial.data.local.converter.DateConverter.Companion.DATE_FORMAT
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateConverter {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @TypeConverter
    fun fromDate(date: LocalDateTime): String = DateTimeFormatter.ofPattern(DATE_FORMAT).format(date)

    @TypeConverter
    fun toDate(strDate: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return LocalDateTime.parse(strDate, formatter)
    }
}