package com.plcoding.bookpedia.book.data.repositories

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.BookDao
import com.plcoding.bookpedia.book.data.database.BookEntity
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.mappers.toBookEntity
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.repositories.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val bookDataSource: RemoteBookDataSource,
    private val dao: BookDao
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
        return try {
            val localResult = dao.getBook(bookId)
            Result.Success(localResult.description)
        } catch (e: Exception) {
            bookDataSource.getBookDetails(bookId).map { dto ->
                dto.description
            }
        }

    }

    override suspend fun getFavoritesBooks(): Flow<List<Book>> {
        return dao.getBooks()
            .map { value: List<BookEntity> ->
                value.map { bookEntity -> bookEntity.toBook() }
            }
    }

    override suspend fun isBookFavorite(id: String): Flow<Boolean> {
        return flow {
            try {
                val book = dao.getBook(id)
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }

        }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            dao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(error = DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Result.Error(error = DataError.Local.UNKNOWN)
        }
    }

    override suspend fun deleteFromFavorite(id: String): EmptyResult<DataError.Local> {
        return try {
            dao.deleteBook(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(error = DataError.Local.UNKNOWN)
        }
    }

}