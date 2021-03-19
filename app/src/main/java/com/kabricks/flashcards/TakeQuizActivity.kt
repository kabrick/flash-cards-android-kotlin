package com.kabricks.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.kabricks.flashcards.database.Card
import com.kabricks.flashcards.database.FlashCardsDatabase

class TakeQuizActivity : AppCompatActivity() {
    lateinit var database: FlashCardsDatabase
    lateinit var questions: MutableList<Card>
    var totalCards: Int = 0
    var currentUserScore: Int = 0
    var areQuestionsDone: Boolean = false
    var isAnswerShown: Boolean = false
    lateinit var questionTextView: TextView
    lateinit var yourAnswerTextView: TextView
    lateinit var correctAnswerTextView: TextView
    lateinit var correctAnswerContainer: TextInputLayout
    lateinit var nextCardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_quiz)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = FlashCardsDatabase.getInstance(this)
        questionTextView = findViewById(R.id.view_card_question)
        yourAnswerTextView = findViewById(R.id.your_card_answer)
        correctAnswerTextView = findViewById(R.id.view_correct_card_answer)
        nextCardButton = findViewById(R.id.next_card_btn)
        correctAnswerContainer = findViewById(R.id.view_correct_card_answer_container)

        val thread = Thread {
            questions = fetchQuestions()

            this.runOnUiThread(Runnable {
                askQuestion()
            })
        }

        thread.start()
    }

    private fun fetchQuestions() : MutableList<Card> {
        // fetch the last batch
        val batch: Int = database.currentScoreDao.getLastRecord()?.quizBatch ?: 0

        var cards: List<Card> = database.cardDao.getNewCards(batch)

        if (cards.isEmpty()) {
            cards = database.cardDao.getTenCards()
        }

        // set number of cards
        totalCards = cards.size

        return cards.toMutableList()
    }

    fun nextCard(view: View) {
        if (isAnswerShown) {
            if (!areQuestionsDone) {
                // hide the correct answer text view
                correctAnswerContainer.visibility = View.GONE
                isAnswerShown = false
                nextCardButton.text = "Show Answer"

                askQuestion()
            }
        } else {
            if (yourAnswerTextView.text.toString().equals(correctAnswerTextView.text.toString())) {
                currentUserScore++
            }

            // check for the correct answer here
            correctAnswerContainer.visibility = View.VISIBLE
            nextCardButton.text = "Next Question"
            isAnswerShown = true
        }
    }

    private fun askQuestion() {
        questionTextView.text = questions[0].question
        yourAnswerTextView.text = ""
        correctAnswerTextView.text = questions[0].answer

        // remove first question
        if (questions.size != 1) {
            questions.removeAt(0)
        } else {
            areQuestionsDone = true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}