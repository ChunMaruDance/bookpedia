package com.plcoding.bookpedia.book.domain.repositories

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface BookRepository {

    suspend fun searchBooks(
        query: String,
    ): Result<List<Book>, DataError.Remote>

}