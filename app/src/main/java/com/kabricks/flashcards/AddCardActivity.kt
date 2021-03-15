package com.kabricks.flashcards

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kabricks.flashcards.database.Card
import com.kabricks.flashcards.database.FlashCardsDatabase
import java.lang.Exception

class AddCardActivity : AppCompatActivity() {
    lateinit var questionTextView: TextView
    lateinit var answerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        questionTextView = findViewById(R.id.add_card_question)
        answerTextView = findViewById(R.id.add_card_answer)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun saveCard(view: View) {
        val thread = Thread {
            val card = Card()
            card.question = questionTextView.text.toString()
            card.answer = answerTextView.text.toString()
            card.quiz_batch = 0

            FlashCardsDatabase.getInstance(this).cardDao.insert(card)

            this.runOnUiThread(Runnable {
                Toast.makeText(this, "Card has been added successfully", Toast.LENGTH_LONG).show()
            })

            startActivity(Intent(this, MainActivity::class.java))
        }

        thread.start()
    }
}