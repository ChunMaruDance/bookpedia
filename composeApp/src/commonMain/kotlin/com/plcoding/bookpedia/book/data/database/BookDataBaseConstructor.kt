package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_EXPECT")
expect object BookDataBaseConstructor:RoomDatabaseConstructor<BookDataBase> {
    override fun initialize(): BookDataBase
}