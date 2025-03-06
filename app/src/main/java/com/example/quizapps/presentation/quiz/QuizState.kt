package com.example.quizapps.presentation.quiz

import com.example.quizapps.domain.model.Quiz


data class QuizState(
    val quiz : Quiz?= null,
    val shuffleOptions : List<String> = emptyList(),
    val selectedOptions : Int ? = -1
)