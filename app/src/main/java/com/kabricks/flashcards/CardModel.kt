package com.kabricks.flashcards

class CardModel (question: String, answer: String) {
    private var question: String
    private var answer: String

    init {
        this.question = question
        this.answer = answer
    }

    fun getQuestion(): String {
        return question
    }

    fun setQuestion(name: String) {
        question = name
    }

    fun getAnswer(): String {
        return answer
    }

    fun setAnswer(name: String) {
        answer = name
    }

}