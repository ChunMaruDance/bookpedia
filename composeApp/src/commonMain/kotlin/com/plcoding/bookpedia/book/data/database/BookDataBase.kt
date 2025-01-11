package com.plcoding.bookpedia.book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [BookEntity::class],
)
@TypeConverters(BookTypeConverters::class)
abstract class BookDataBase : RoomDatabase() {

    abstract fun getBookDao(): BookDao

    companion object {
        const val DB_NAME = "books.db"
    }

}