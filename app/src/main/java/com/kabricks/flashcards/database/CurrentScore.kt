package com.kabricks.flashcards.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_score")
data class CurrentScore (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "score")
    var score: Int = -1,

    @ColumnInfo(name = "quiz_batch")
    var quizBatch: Int = 0
)