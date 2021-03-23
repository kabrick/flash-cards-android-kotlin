package com.kabricks.flashcards

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizStatusModel(
        var question: String,
        var yourAnswer: String,
        var correctAnswer: String,
        var isCorrect: Boolean): Parcelable