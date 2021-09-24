package com.example.settings.database

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey @ColumnInfo(name = "book_id") val book_id:String,
    @ColumnInfo(name = "book_title")val book_title:String,
    @ColumnInfo(name = "book_author")val book_author:String,
    @ColumnInfo(name = "book_date")val book_date: String,
    @ColumnInfo(name = "book_category")val book_category: String,
    @ColumnInfo(name = "book_thumbnail")val book_thumbnail: String,
    @ColumnInfo(name = "book_price")val book_price: String,
)
