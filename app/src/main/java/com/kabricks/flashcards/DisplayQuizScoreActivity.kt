package com.kabricks.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var answersStatus: ArrayList<QuizStatusModel> = arrayListOf()
    private lateinit var quizAdapter: QuizStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_quiz_score)

        database = FlashCardsDatabase.getInstance(this)

        userScore = intent.getIntExtra("score", 0)
        numberOfQuestions = intent.getIntExtra("number_of_questions", 0)
        batch = intent.getIntExtra("batch", 0)
        answersStatus = intent.getParcelableArrayListExtra<QuizStatusModel>("quiz_status") as ArrayList<QuizStatusModel>

        val recyclerView: RecyclerView = findViewById(R.id.answers_status_recycler_view)
        quizAdapter = QuizStatusAdapter(answersStatus)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = quizAdapter
        quizAdapter.notifyDataSetChanged()

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