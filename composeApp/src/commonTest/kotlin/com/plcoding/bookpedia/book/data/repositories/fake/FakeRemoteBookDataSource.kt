package com.plcoding.bookpedia.book.data.repositories.fake

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

class FakeRemoteBookDataSource : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return Result.Success(
            SearchResponseDto(
                listOf(
                    SearchedBookDto(id = "1", title = "Kotlin Programming", coverKey = "1")
                )
            )
        )
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return Result.Success(
            BookWorkDto("Some")
        )
    }

}