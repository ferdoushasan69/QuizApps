package com.example.quizapps.presentation.quiz

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapps.R
import com.example.quizapps.common.Resource
import com.example.quizapps.domain.model.Quiz
import com.example.quizapps.domain.usecase.GetQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizUseCase: GetQuizUseCase,
    private val application: Application
) : ViewModel() {
    private var _quizState = MutableStateFlow(QuizStateScreen())
    val quizState = _quizState

    private var mediaPlayer: MediaPlayer? = null
    fun onEvent(quizEvent: QuizEvent) {
        when (quizEvent) {
            is QuizEvent.GetQuiz -> {
                getQuiz(
                    quizSize = quizEvent.quizSize,
                    category = quizEvent.category,
                    difficulty = quizEvent.difficulty,
                    type = quizEvent.type
                )
            }

            is QuizEvent.UpdateQuizIndex -> {
                updateQuizListIndex(context = application,quizEvent.quizListOfIndex, quizEvent.selectedOptionIndex)

            }
        }
    }

    private fun updateQuizListIndex(context: Context,quizListOfIndex: Int, selectedOptionIndex: Int) {
        val quizStates = quizState.value.quizList[quizListOfIndex]
        val correctAnswer = quizStates.quiz?.correct_answer
        val selectedAnswer = quizStates.shuffleOptions[selectedOptionIndex]
        val cleanedSelectedAnswer = selectedAnswer.replace("&quot;", "\"").replace("&#039;", "'")
        val cleanedCorrectAnswer = correctAnswer?.replace("&quot;", "\"")?.replace("&#039;", "'")
        val isCorrect = cleanedSelectedAnswer == cleanedCorrectAnswer

        playSound(
            context = context,
            isCorrect = isCorrect
        )
        val updateQuizList = mutableListOf<QuizState>()
        val updateList = _quizState.value.quizList.mapIndexed { index, quizState ->
                if (quizListOfIndex == index) {
                    quizState.copy(selectedOptions = selectedOptionIndex)
                } else {
                    quizState
                }
        }
        _quizState.value = quizState.value .copy(quizList = updateList)
        updateQuizScore(quizState.value.quizList[quizListOfIndex])


    }

    private fun updateQuizScore(quizState: QuizState) {
        if (quizState.selectedOptions != -1) {
            val correctAnswer = quizState.quiz?.correct_answer
            val selectedAnswer = quizState.selectedOptions?.let {
                quizState.shuffleOptions[it].replace("&quot;", "\"").replace("&#039;", "\'")
            }
            if (correctAnswer == selectedAnswer) {
                val totalScore = _quizState.value.score
                _quizState.value = _quizState.value.copy(score = totalScore + 1)
            }
        }

    }

    private fun getQuiz(quizSize: Int, category: Int, difficulty: String, type: String) {
        viewModelScope.launch {
            getQuizUseCase.invoke(
                amount = quizSize,
                category = category,
                difficulty = difficulty,
                type = type
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.d(TAG, "Error Quiz viewmodel : ${result.error}")
                        _quizState.value = QuizStateScreen(error = result.error)
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "getQuiz: loading")
                        _quizState.value = QuizStateScreen(isLoading = true)
                    }

                    is Resource.Success -> {
                        val listOfQuizState: List<QuizState> = getListOfQuizState(result.data)
                        _quizState.value = QuizStateScreen(quizList = listOfQuizState)
                    }
                }
            }
        }
    }

    private fun getListOfQuizState(quizList: List<Quiz>?): List<QuizState> {
        val listOfQuizState = mutableListOf<QuizState>()
        for (quiz in quizList!!) {
            val shuffleOptions = mutableListOf<String>().apply {
                add(quiz.correct_answer)
                addAll(quiz.incorrect_answers)
                shuffle()
            }
            listOfQuizState.add(QuizState(quiz, shuffleOptions = shuffleOptions, -1))
        }
        return listOfQuizState
    }

    private fun playSound(context: Context, isCorrect: Boolean) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, if (isCorrect) R.raw.correct else R.raw.wrong)
        mediaPlayer?.start()
    }
}