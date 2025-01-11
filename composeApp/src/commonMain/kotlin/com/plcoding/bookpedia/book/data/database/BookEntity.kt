package com.plcoding.bookpedia.book.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "books"
)
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "title") val title: String,
    val description: String,
    val imgUrl: String,
    val languages: List<String>,
    val authors: List<String>,
    val firstPublishYear: String?,
    val ratingAverage: Double?,
    val ratingCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int


)