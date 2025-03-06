package com.example.quizapps.data.remote

import com.example.quizapps.data.model.QuizResponse
import com.example.quizapps.domain.model.QuizList
import com.example.quizapps.utils.END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(END_POINT)
    suspend fun getQuiz(
        @Query("amount") amount : Int,
        @Query("category")category:Int,
        @Query("difficulty") difficulty : String,
        @Query("type") type : String
    ): QuizResponse
}