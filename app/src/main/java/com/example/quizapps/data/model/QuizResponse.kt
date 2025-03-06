package com.example.quizapps.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizResponse(
    @SerialName("response_code")
    val responseCode: Int = 0,
    @SerialName("results")
    val results: List<QuizDto> = listOf()
)