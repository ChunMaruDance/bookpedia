package com.plcoding.bookpedia.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.*

actual class DataBaseFactory {

    actual fun create(): RoomDatabase.Builder<BookDataBase> {
        val dbFile = documentDirectory() + "/${BookDataBase.DB_NAME}"
        return Room.databaseBuilder<BookDataBase>(dbFile)
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )

        return requireNotNull(documentDirectory?.path)
    }
}