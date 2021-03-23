package com.kabricks.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class QuizStatusAdapter(private var quizList: List<QuizStatusModel>) :
        RecyclerView.Adapter<QuizStatusAdapter.MyViewHolder>()  {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listQuestion: TextView = view.findViewById(R.id.list_question)
        var listYourAnswer: TextView = view.findViewById(R.id.list_your_answer)
        var listCorrectAnswer: TextView = view.findViewById(R.id.list_correct_answer)
        var listStatus: ImageView = view.findViewById(R.id.list_status)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_display_quiz_score, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.listQuestion.text = quiz.question
        holder.listYourAnswer.text = "Your Answer: " + quiz.yourAnswer
        holder.listCorrectAnswer.text = "Correct Answer: " + quiz.correctAnswer

        if (quiz.isCorrect) {
            holder.listStatus.setImageResource(R.drawable.correct)
        } else {
            holder.listStatus.setImageResource(R.drawable.wrong)
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }
}