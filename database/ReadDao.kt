package com.example.settings.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReadDao {
    @Insert
    fun insertRead(readEntity: ReadEntity)

    @Delete
    fun DeleteRead(readEntity: ReadEntity)

    @Query("SELECT * FROM read")
    fun getAllRead(): List<ReadEntity>

    @Query("SELECT * FROM read WHERE rbook_id= :readId")
    fun getReadById(readId:String):ReadEntity
}