package com.example.quizapps.domain.repository

import com.example.quizapps.domain.model.QuizList

interface QuizRepository {
    suspend fun getQuiz(amount : Int,category : Int,difficulty : String,types : String) : QuizList
}