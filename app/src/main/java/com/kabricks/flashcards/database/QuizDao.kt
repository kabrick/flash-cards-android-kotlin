package com.kabricks.flashcards.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuizDao {
    @Insert
    fun insert(score : Quiz)

    @Update
    fun update(score : Quiz)

    @Query("SELECT * from quiz WHERE id = :key")
    fun get(key: Long): Quiz?

    @Query("DELETE FROM quiz")
    fun clearAll()

    @Query("SELECT * FROM quiz ORDER BY id DESC LIMIT 1")
    fun getLastRecord(): Quiz?

    @Query("SELECT * FROM quiz ORDER BY id DESC")
    fun getAllRecords(): LiveData<List<Quiz>>
}