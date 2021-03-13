package com.kabricks.flashcards.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardDao {
    @Insert
    fun insert(score : Card)

    @Update
    fun update(score : Card)

    @Query("SELECT * from cards WHERE id = :key")
    fun get(key: Long): Card?

    @Query("DELETE FROM cards")
    fun clearAll()

    @Query("SELECT * FROM cards ORDER BY id DESC LIMIT 1")
    fun getLastRecord(): Card?

    @Query("SELECT * FROM cards ORDER BY id DESC")
    fun getAllRecords(): LiveData<List<Card>>
}