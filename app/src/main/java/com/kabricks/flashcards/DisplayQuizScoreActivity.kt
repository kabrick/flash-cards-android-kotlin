package com.kabricks.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.kabricks.flashcards.database.CurrentScore
import com.kabricks.flashcards.database.FlashCardsDatabase
import com.kabricks.flashcards.database.Quiz

class DisplayQuizScoreActivity : AppCompatActivity() {
    lateinit var database: FlashCardsDatabase
    private var userScore: Int = 0
    private var numberOfQuestions: Int = 0
    private var batch: Int = 0
    private lateinit var scoreTextView: TextView
    private lateinit var motivationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_quiz_score)

        database = FlashCardsDatabase.getInstance(this)

        userScore = intent.getIntExtra("score", 0)
        numberOfQuestions = intent.getIntExtra("number_of_questions", 0)
        batch = intent.getIntExtra("batch", 0)

        // save the score - and the date!

        val thread = Thread {
            val previousScore: Int = database.currentScoreDao.getLastRecord()?.score ?: 0
            val currentScore = CurrentScore()
            currentScore.id = 1
            currentScore.previous_score = previousScore
            currentScore.score = userScore
            currentScore.quizBatch = batch + 1

            scoreTextView = findViewById(R.id.display_score)
            motivationTextView = findViewById(R.id.display_motivation)

            this.runOnUiThread {
                scoreTextView.text = "$userScore / $numberOfQuestions"

                if (previousScore > userScore) {
                    motivationTextView.text = "Nearly there. Keep practicing"
                } else if (previousScore < userScore) {
                    motivationTextView.text = "Getting better. Keep practicing"
                } else {
                    motivationTextView.text = "Consistency is key. Keep practicing"
                }
            }

            val quiz = Quiz()
            quiz.score = userScore
            quiz.timeTaken = System.currentTimeMillis()

            database.quizDao.insert(quiz)
            database.currentScoreDao.update(currentScore)
        }
        thread.start()
    }

    fun goToDashboard(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun retryQuiz(view: View) {
        startActivity(Intent(this, TakeQuizActivity::class.java))
    }
}