package com.example.quizapps.presentation.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.quizapps.presentation.home.HomeScreen
import com.example.quizapps.presentation.home.HomeViewModel
import com.example.quizapps.presentation.quiz.QuizScreen
import com.example.quizapps.presentation.quiz.QuizViewModel
import com.example.quizapps.presentation.result.ResultScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
   NavHost(navController = navController,
       startDestination = Home,
       enterTransition = {
           slideInHorizontally (
               animationSpec = tween(400),
               initialOffsetX ={1000}
           )

       }, exitTransition = {
           slideOutHorizontally  (
               animationSpec = tween(400),
               targetOffsetX = {-1000}
           )
       }
   ){

       composable<Home> {
           val viewmodel : HomeViewModel = hiltViewModel()
           val state by viewmodel.homeState.collectAsState()
           HomeScreen(
               state = state,
               event = viewmodel::onEvent,
               onGenerateQuizClick = {size,category,difficulty,type->
                   navController.navigate(Quiz(
                       quizSize = size,
                       category = category,
                       difficulty = difficulty,
                       type = type
                   ))
               }
           )
       }

       composable<Quiz> {
           val viewModel : QuizViewModel = hiltViewModel()
           val state by viewModel.quizState.collectAsState()
           val args = it.toRoute<Quiz>()
           QuizScreen(
               quizSize = args.quizSize,
               category = args.category,
               difficulty = args.difficulty,
               type = args.type,
               state = state,
               event = viewModel::onEvent,
               backToHome = { navController.navigateUp() },
               navigateToResult = {score,totalsize->
                   navController.navigate(Result(score,totalsize))
               }
           )
       }
       composable<Result> {
           val args = it.toRoute<Result>()
           ResultScreen(
               totalScore = args.score,
               quizSize = args.size,
               backHome = {
                   navController.navigate(Home)
               }
           )
       }
   }
    
}