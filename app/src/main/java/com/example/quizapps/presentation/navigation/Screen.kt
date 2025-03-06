package com.example.quizapps.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
data object Home

@Serializable
data class Quiz(val quizSize: Int, val category: String, val difficulty: String, val type: String)

@Serializable
data class Result(val score : Int,val size : Int)