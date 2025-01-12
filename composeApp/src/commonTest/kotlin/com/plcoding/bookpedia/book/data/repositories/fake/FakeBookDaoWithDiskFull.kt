package com.plcoding.bookpedia.book.data.repositories.fake

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.BookDao
import com.plcoding.bookpedia.book.data.database.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.io.IOException

class FakeBookDaoWithDiskFull : BookDao {


    override suspend fun upsert(book: BookEntity) {
        // disk full
        throw SQLiteException("Disk is full")
    }

    override suspend fun deleteBook(id: String) {
        // disk full
        throw SQLiteException("Disk is full")
    }

    override fun getBooks(): Flow<List<BookEntity>> {
        return flowOf(emptyList())
    }

    override suspend fun getBook(id: String): BookEntity {
        return BookEntity(
            id = id,
            title = "Fake Book",
            description = "Description",
            imgUrl = "",
            languages = listOf(),
            authors = listOf(),
            numEditions = 0,
            ratingCount = 0,
            ratingAverage = 0.0,
            numPagesMedian = 0,
            firstPublishYear = ""
        )
    }

}