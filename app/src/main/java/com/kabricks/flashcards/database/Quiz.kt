package com.kabricks.flashcards.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
data class Quiz (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "score")
    var score: Int = 0,

    @ColumnInfo(name = "time_taken")
    var timeTaken: Long = System.currentTimeMillis()
)