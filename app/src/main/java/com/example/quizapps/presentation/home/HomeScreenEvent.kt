package com.example.quizapps.presentation.home


sealed class HomeScreenEvent {
    data class SetNumberOfQuiz(val totalQuizNumber: Int) : HomeScreenEvent()
    data class SetCategoryOfQuiz(val categoryOfQuiz: String) : HomeScreenEvent()
    data class SetDifficultyOfQuiz(val difficulty: String) : HomeScreenEvent()
    data class SetTypesOfQuiz(val type: String) : HomeScreenEvent()
}