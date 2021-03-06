package com.kabricks.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kabricks.flashcards.database.FlashCardsDatabase

class ManageCardsActivity : AppCompatActivity() {
    private val cardList = ArrayList<CardModel>()
    private lateinit var cardsAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_cards)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView: RecyclerView = findViewById(R.id.manage_cards_recycler_view)
        cardsAdapter = CardsAdapter(cardList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = cardsAdapter

        val thread = Thread {
            //fetch Records
            FlashCardsDatabase.getInstance(this)
                .cardDao
                .getAllRecords()
                .forEach() {
                    val card = CardModel(it.question, it.answer)
                    cardList.add(card)
                }

            cardsAdapter.notifyDataSetChanged()
        }

        thread.start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}