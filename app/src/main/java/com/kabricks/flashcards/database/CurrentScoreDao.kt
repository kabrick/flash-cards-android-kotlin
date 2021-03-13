package com.kabricks.flashcards.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrentScoreDao {
    @Insert
    fun insert(score : CurrentScore)

    @Update
    fun update(score : CurrentScore)

    @Query("SELECT * from current_score WHERE id = :key")
    fun get(key: Long): CurrentScore?

    @Query("DELETE FROM current_score")
    fun clearAll()

    @Query("SELECT * FROM current_score ORDER BY id DESC LIMIT 1")
    fun getLastRecord(): CurrentScore?

    @Query("SELECT * FROM current_score ORDER BY id DESC")
    fun getAllRecords(): LiveData<List<CurrentScore>>
}