package com.kabricks.flashcards

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            val currentScore: CurrentScore? = database.currentScoreDao.getLastRecord()
            val currentScoreNumber: Int? = currentScore?.score
            val lastScore: Int? = currentScore?.previous_score

            if (currentScoreNumber === null) {
                initializeDatabase()
            } else {
                currentScoreTextView.setText(currentScoreNumber.toString())

                if (currentScoreNumber > lastScore!!) {
                    currentScoreTextView.setTextColor(Color.parseColor("#009933"))
                } else if (currentScoreNumber < lastScore) {
                    currentScoreTextView.setTextColor(Color.parseColor("#ff3300"))
                } else {
                    currentScoreTextView.setTextColor(Color.parseColor("#ffcc66"))
                }
            }
        }
        thread.start()
    }

    fun initializeDatabase() {
        val currentScoreModel = CurrentScore()
        currentScoreModel.quizBatch = 0
        currentScoreModel.score = 0
        currentScoreModel.previous_score = 0

        database.currentScoreDao.insert(currentScoreModel)
    }

    fun addCard(view: View) {
        startActivity(Intent(this, AddCardActivity::class.java))
    }

    fun manageCards(view: View) {
        startActivity(Intent(this, ManageCardsActivity::class.java))
    }
}