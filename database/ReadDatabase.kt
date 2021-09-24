package com.example.settings.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ReadEntity::class],version = 1)
abstract class ReadDatabase:RoomDatabase() {
    abstract fun readDao():ReadDao
}