package com.plcoding.bookpedia.book.data.repositories.fake

import com.plcoding.bookpedia.book.data.database.BookDao
import com.plcoding.bookpedia.book.data.database.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBookDao : BookDao {
    private val books = (1..10).map { numb ->
        BookEntity(
            id = "$numb",
            title = "Kotlin Programming",
            description = "Some description $numb",
            imgUrl = "$numb",
            languages = listOf(),
            authors = listOf(),
            numEditions = numb,
            ratingCount = numb,
            ratingAverage = numb.toDouble(),
            numPagesMedian = numb,
            firstPublishYear = "$numb"
        )
    }.toMutableList()


    override suspend fun getBook(id: String) = books.first { it.id == id }

    override suspend fun upsert(book: BookEntity) {
        books.add(book)
    }

    override suspend fun deleteBook(id: String) {
        books.removeAll { it.id == id }
    }

    override fun getBooks(): Flow<List<BookEntity>> = flow {
        emit(books)
    }

}