package com.example.quizapps.domain.usecase

import com.example.quizapps.common.Resource
import com.example.quizapps.domain.model.Quiz
import com.example.quizapps.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {
    operator fun invoke(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): Flow<Resource<List<Quiz>>> = flow {
        emit(Resource.Loading)

        try {
            val response = quizRepository.getQuiz(amount, category, difficulty, type)
            emit(Resource.Success(response.results))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}