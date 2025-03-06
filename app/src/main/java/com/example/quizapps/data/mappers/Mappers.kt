package com.example.quizapps.data.mappers

import com.example.quizapps.data.model.QuizDto
import com.example.quizapps.data.model.QuizResponse
import com.example.quizapps.domain.model.Quiz
import com.example.quizapps.domain.model.QuizList

fun QuizDto.toDomainQuiz()=Quiz(
    category = category,
    correct_answer = correct_answer,
    difficulty = difficulty,
    incorrect_answers = incorrect_answers,
    question = question,
    type = type
)

fun QuizResponse.toDomainQuizList()=QuizList(
    responseCode = responseCode,
    results = results.map { it.toDomainQuiz() }
)