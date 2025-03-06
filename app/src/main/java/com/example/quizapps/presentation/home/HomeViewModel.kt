package com.example.quizapps.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel:ViewModel() {
    private var _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState

    fun onEvent(event: HomeScreenEvent){
        when(event){
            is HomeScreenEvent.SetCategoryOfQuiz -> {
                _homeState.value = _homeState.value.copy(category = event.categoryOfQuiz)
            }
            is HomeScreenEvent.SetDifficultyOfQuiz -> {
                _homeState.value = _homeState.value.copy(difficulty = event.difficulty)
            }
            is HomeScreenEvent.SetNumberOfQuiz -> {
                _homeState.value = _homeState.value.copy(quizSize = event.totalQuizNumber)
            }
            is HomeScreenEvent.SetTypesOfQuiz -> {
                _homeState.value = _homeState.value.copy(type = event.type)
            }
        }
    }
}