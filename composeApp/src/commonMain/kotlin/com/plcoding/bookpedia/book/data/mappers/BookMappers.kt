package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book


fun SearchedBookDto.toBook(): Book = Book(
    id = id.substringAfterLast("/"),
    title = title,
    ratingCount = ratingCount,
    averageRating = ratingAverage,
    imageUrl = if (coverKey != null) "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
    else "https://covers.openlibrary.org/b/olid/${coverAlternativeKey}-L.jpg",
    authors = authorNames ?: emptyList(),
    languages = languages ?: emptyList(),
    description = null,
    firstPublishYear = publishYear.toString(),
    numPages = numPagesMedium,
    numEditions = numEditions ?: 0
)


