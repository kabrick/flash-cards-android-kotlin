package com.kabricks.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kabricks.flashcards.database.CurrentScore
import com.kabricks.flashcards.database.FlashCardsDatabase

class MainActivity : AppCompatActivity() {
    lateinit var currentScoreTextView : TextView
    lateinit var database: FlashCardsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentScoreTextView = findViewById(R.id.current_score)
        database = FlashCardsDatabase.getInstance(this)

        val thread = Thread {
            val lastScore: Int? = database.currentScoreDao.getLastRecord()?.score

            if (lastScore === null) {
                initializeDatabase()
            } else {
                currentScoreTextView.setText(lastScore.toString())
            }

            //fetch Records
            /*db.bookDao().getAllBooks().forEach() {
                Log.i("Fetch Records", "Id:  : ${it.bookId}")
                Log.i("Fetch Records", "Name:  : ${it.bookName}")
            }*/
        }
        thread.start()
    }

    fun initializeDatabase() {
        val currentScoreModel = CurrentScore()
        currentScoreModel.quizBatch = 0
        currentScoreModel.score = 0

        database.currentScoreDao.insert(currentScoreModel)
    }
}