package com.kabricks.flashcards.database

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
    fun getAllRecords(): List<Card>

    @Query("SELECT * FROM cards WHERE quiz_batch != :key")
    fun getNewCards(key: Int): List<Card>

    @Query("SELECT * FROM cards ORDER BY quiz_batch ASC LIMIT 10")
    fun getTenCards(): List<Card>
}