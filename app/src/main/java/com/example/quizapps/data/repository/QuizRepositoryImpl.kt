package com.example.quizapps.data.repository

import com.example.quizapps.data.mappers.toDomainQuizList
import com.example.quizapps.data.remote.ApiService
import com.example.quizapps.domain.model.QuizList
import com.example.quizapps.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val apiService: ApiService
):QuizRepository {
    override suspend fun getQuiz(
        amount: Int,
        category: Int,
        difficulty: String,
        types: String
    ): QuizList = apiService.getQuiz(amount,category,difficulty,types).toDomainQuizList()
}