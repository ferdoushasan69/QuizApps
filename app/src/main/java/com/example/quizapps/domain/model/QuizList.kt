package com.example.quizapps.domain.model




data class QuizList(
    val responseCode: Int,
    val results: List<Quiz>
)