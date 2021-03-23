package com.kabricks.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class CardsAdapter(private var cardsList: List<CardModel>) :
    RecyclerView.Adapter<CardsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listQuestion: TextView = view.findViewById(R.id.list_question)
        var listAnswer: TextView = view.findViewById(R.id.list_answer)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_manage_cards, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cards = cardsList[position]
        holder.listAnswer.text = cards.getAnswer()
        holder.listQuestion.text = cards.getQuestion()
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }
}