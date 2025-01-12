package com.plcoding.bookpedia.book.data.repositories

import com.plcoding.bookpedia.book.data.database.BookDao
import com.plcoding.bookpedia.book.data.database.BookEntity
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.repositories.fake.FakeBookDao
import com.plcoding.bookpedia.book.data.repositories.fake.FakeBookDaoWithDiskFull
import com.plcoding.bookpedia.book.data.repositories.fake.FakeRemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.domain.DataError
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.toList

class BookRepositoryImplTest {

    private lateinit var bookDataSource: RemoteBookDataSource
    private lateinit var dao: BookDao
    private lateinit var repository: BookRepositoryImpl

    @BeforeTest
    fun setup() {
        bookDataSource = FakeRemoteBookDataSource()
        dao = FakeBookDao()
        repository = BookRepositoryImpl(bookDataSource, dao)
    }

    @Test
    fun `searchBooks should return mapped books`() = runTest {
        // Arrange
        val query = "Kotlin"
        val expectedBooks = listOf(
            SearchedBookDto(id = "1", title = "Kotlin Programming", coverKey = "1").toBook()
        )
        // Act
        val result = repository.searchBooks(query)

        // Assert
        assertEquals(
            Result.Success(expectedBooks),
            result
        )
    }

    @Test
    fun `getBookDescription should return description from local data`() = runTest {
        // Arrange
        val bookId = 1
        val expectedDescription = "Some description $bookId"

        dao.upsert(
            BookEntity(
                id = "$bookId",
                title = "Kotlin Programming",
                description = expectedDescription,
                imgUrl = "$bookId",
                languages = listOf(),
                authors = listOf(),
                numEditions = bookId,
                ratingCount = bookId,
                ratingAverage = bookId.toDouble(),
                numPagesMedian = bookId,
                firstPublishYear = "$bookId"
            )
        )

        // Act
        val result = repository.getBookDescription(bookId.toString())

        // Assert
        assertEquals(Result.Success(expectedDescription), result)
    }

    @Test
    fun `getBookDescription should return description from remote data when not found locally`() =
        runTest {
            // Arrange
            val bookId = "2"
            val expectedDescription = "Some description $bookId"
            val fakeDao = FakeBookDao()
            val fakeRemoteBookDataSource = FakeRemoteBookDataSource()

            fakeRemoteBookDataSource.searchBooks("Kotlin Programming")
            val repository = BookRepositoryImpl(fakeRemoteBookDataSource, fakeDao)

            // Act
            val result = repository.getBookDescription(bookId)

            // Assert
            assertEquals(Result.Success(expectedDescription), result)
        }

    @Test
    fun `getFavoritesBooks should return list of books`() = runTest {
        // Arrange
        val expectedBooks: List<Book> = (1..10).map { numb ->
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
            ).toBook()
        }

        val fakeDao = FakeBookDao()
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val resultBooks = repository.getFavoritesBooks().toList()[0]

        // Assert
        assertEquals(expectedBooks, resultBooks)
    }

    @Test
    fun `isBookFavorite should return true if book is in favorites`() = runTest {
        // Arrange
        val bookId = "-1"
        val fakeDao = FakeBookDao()
        val book = BookEntity(
            id = bookId,
            title = "Kotlin Programming",
            description = "Some description",
            imgUrl = bookId,
            languages = listOf(),
            authors = listOf(),
            numEditions = 1,
            ratingCount = 1,
            ratingAverage = 5.0,
            numPagesMedian = 100,
            firstPublishYear = "2021"
        )
        fakeDao.upsert(book)
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val result = repository.isBookFavorite(bookId).toList().first()

        // Assert
        assertEquals(true, result)
    }

    @Test
    fun `isBookFavorite should return false if book is not in favorites`() = runTest {
        // Arrange
        val bookId = "-1"
        val fakeDao = FakeBookDao()
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val result = repository.isBookFavorite(bookId).toList().first()

        // Assert
        assertEquals(false, result)
    }

    @Test
    fun `markAsFavorite should add book to favorites successfully`() = runTest {
        // Arrange
        val book = BookEntity(
            id = "-1",
            title = "Kotlin Programming",
            description = "Some description -1",
            imgUrl = "-1",
            languages = listOf(),
            authors = listOf(),
            numEditions = -1,
            ratingCount = -1,
            ratingAverage = (-1).toDouble(),
            numPagesMedian = -1,
            firstPublishYear = "-1"
        ).toBook()
        val fakeDao = FakeBookDao()
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val result = repository.markAsFavorite(book)

        // Assert
        assertEquals(Result.Success(Unit), result)
    }

    @Test
    fun `markAsFavorite should return error when unable to save book due to disk full`() = runTest {
        // Arrange
        val book = BookEntity(
            id = "-1",
            title = "Kotlin Programming",
            description = "Some description -1",
            imgUrl = "-1",
            languages = listOf(),
            authors = listOf(),
            numEditions = -1,
            ratingCount = -1,
            ratingAverage = (-1).toDouble(),
            numPagesMedian = -1,
            firstPublishYear = "-1"
        ).toBook()

        val fakeDao = FakeBookDaoWithDiskFull()
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val result = repository.markAsFavorite(book)

        // Assert
        assertEquals(Result.Error(DataError.Local.DISK_FULL), result)
    }

    @Test
    fun `deleteFromFavorite should remove book from favorites successfully`() = runTest {
        // Arrange
        val bookId = "1"
        val fakeDao = FakeBookDao()
        val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

        // Act
        val result = repository.deleteFromFavorite(bookId)

        // Assert
        assertEquals(Result.Success(Unit), result)
    }

    @Test
    fun `deleteFromFavorite should return error when unable to delete book due to an unknown error`() =
        runTest {
            // Arrange
            val bookId = "1"
            val fakeDao = FakeBookDaoWithDiskFull()
            val repository = BookRepositoryImpl(FakeRemoteBookDataSource(), fakeDao)

            // Act
            val result = repository.deleteFromFavorite(bookId)

            // Assert
            assertEquals(Result.Error(DataError.Local.UNKNOWN), result)
        }


}