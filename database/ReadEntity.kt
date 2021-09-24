package com.example.settings.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "read")
data class ReadEntity(
    @PrimaryKey @ColumnInfo(name = "rbook_id") val read_id:String,
    @ColumnInfo(name = "rbook_title")val read_title:String,
    @ColumnInfo(name = "rbook_author")val read_author:String,
    @ColumnInfo(name = "rbook_date")val read_date: String,
    @ColumnInfo(name = "rbook_category")val read_category: String,
    @ColumnInfo(name = "rbook_thumbnail")val read_thumbnail: String,
    @ColumnInfo(name = "rbook_price")val read_price: String,
)