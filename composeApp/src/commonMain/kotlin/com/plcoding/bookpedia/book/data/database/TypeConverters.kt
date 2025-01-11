package com.plcoding.bookpedia.book.data.database


import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object BookTypeConverters {

    @TypeConverter
    fun listToString(value: List<String>): String {
        return Json.encodeToString(serializer(), value)
    }

    @TypeConverter
    fun listFromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

}