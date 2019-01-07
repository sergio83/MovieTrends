package com.billboard.movies.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun stringToListOfInt(json: String): List<Int>? {
        return  Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun listOfIntToString(list: List<Int>?): String? {
        return Gson().toJson(list)
    }

}