package com.kabricks.flashcards.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "question")
    var question: String = "",

    @ColumnInfo(name = "answer")
    var answer: String = "",

    @ColumnInfo(name = "quiz_batch")
    var quiz_batch: Int = 0
)