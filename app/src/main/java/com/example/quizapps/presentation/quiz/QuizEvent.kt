package com.example.quizapps.presentation.quiz

sealed class QuizEvent {
    data class GetQuiz(
        val quizSize: Int,
        val category: Int,
        val difficulty: String,
        val type: String
    ) : QuizEvent()

    data class UpdateQuizIndex(val quizListOfIndex: Int, val selectedOptionIndex: Int) : QuizEvent()
}