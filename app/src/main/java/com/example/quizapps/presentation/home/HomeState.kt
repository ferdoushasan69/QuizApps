package com.example.quizapps.presentation.home

data class HomeState(
    val quizSize : Int=10,
    val category : String ="General Knowledge",
    val difficulty : String = "Easy",
    val type : String = "Multiple Choice"
)
