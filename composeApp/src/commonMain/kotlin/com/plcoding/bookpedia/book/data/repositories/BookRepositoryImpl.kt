package com.plcoding.bookpedia.book.data.repositories

import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.repositories.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

class BookRepositoryImpl(
    private val bookDataSource: RemoteBookDataSource
) : BookRepository {

    override suspend fun searchBooks(
        query: String,
    ): Result<List<Book>, DataError.Remote> {
        return bookDataSource.searchBooks(
            query
        ).map { dto ->
            dto.results.map { it.toBook() }
        }

    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        return bookDataSource.getBookDetails(bookId).map { dto ->
            dto.description
        }
    }

}