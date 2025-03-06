package com.example.quizapps.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizDto(
    @SerialName("category")
    val category: String = "",
    @SerialName("correct_answer")
    val correct_answer: String = "",
    @SerialName("difficulty")
    val difficulty: String = "",
    @SerialName("incorrect_answers")
    val incorrect_answers: List<String> = listOf(),
    @SerialName("question")
    val question: String = "",
    @SerialName("type")
    val type: String = ""
)