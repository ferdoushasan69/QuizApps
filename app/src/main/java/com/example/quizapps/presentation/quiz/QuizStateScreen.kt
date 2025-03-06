package com.example.quizapps.presentation.quiz

data class QuizStateScreen(
    val isLoading : Boolean = false,
    val quizList : List<QuizState> = emptyList(),
    val error : String? = "",
    val score : Int = 0
)
