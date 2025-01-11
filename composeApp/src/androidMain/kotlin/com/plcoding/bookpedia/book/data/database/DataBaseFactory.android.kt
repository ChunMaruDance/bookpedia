package com.plcoding.bookpedia.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DataBaseFactory(
    private val content: Context
) {
    actual fun create(): RoomDatabase.Builder<BookDataBase> {
        val dbPath = content.applicationContext.getDatabasePath(BookDataBase.DB_NAME)
        return Room.databaseBuilder(
            context = content,
            name = dbPath.absolutePath
        )
    }

}