package com.example.quizapps.presentation.quiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapps.presentation.component.HomeHeader
import com.example.quizapps.presentation.quiz.component.QuizContent
import com.example.quizapps.presentation.quiz.component.ShimmerEffect
import com.example.quizapps.ui.theme.bgColor
import com.example.quizapps.ui.theme.optionSelectedColor
import com.example.quizapps.ui.theme.optionUnSelectedColor
import com.example.quizapps.ui.theme.selectionTextColor
import com.example.quizapps.utils.K
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quizSize: Int,
    category: String,
    difficulty: String,
    type: String,
    state: QuizStateScreen,
    event: (QuizEvent) -> Unit,
    backToHome: () -> Unit,
    navigateToResult:(Int,Int)->Unit
) {
    BackHandler {
        backToHome()
    }
    val uiState by rememberUpdatedState(state)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { state.quizList.size }

    LaunchedEffect(Unit) {
        val mapDifficulties = when (difficulty) {
            "Easy" -> "easy"
            "Medium" -> "medium"
            else -> "hard"
        }

        val mapTypes = when (type) {
            "Multiple Choice" -> "multiple"
            else -> "boolean"
        }
        event(
            QuizEvent.GetQuiz(
                quizSize = quizSize,
                category = K.categoriesMap[category]!!,
                difficulty = mapDifficulties,
                type = mapTypes
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

    ) {
        Spacer(Modifier.height(60.dp))
        QuizHeader(
            backToHome = backToHome,
            category = category
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Question : $quizSize",
                fontSize = 22.sp,
                color = Color.White
            )
            Text(difficulty, fontSize = 22.sp, color = Color.White)
        }
        HorizontalDivider()


        if (fetchQuiz(state)) {
            val buttonText by remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        0 -> {
                            listOf("", "Next")
                        }

                        state.quizList.size - 1 -> {
                            listOf("Previous", "Submit")
                        }

                        else -> {
                            listOf("Previous", "Next")
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f)
            ){
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                beyondViewportPageCount = 1
            ) { index ->
                Column {

                    QuizContent(
                        onQuizOptionClick = { selectedIndex ->
                            event(
                                QuizEvent.UpdateQuizIndex(
                                    quizListOfIndex = index,
                                    selectedOptionIndex = selectedIndex
                                )
                            )
                        },
                        qNumber = index + 1,
                        state = state.quizList[index]
                    )
                }

                    Spacer(Modifier.height(8.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .navigationBarsPadding()
            ){
                if (buttonText[0].isNotEmpty()){
                    ButtonBox(
                        text = "Previous",
                        color = optionSelectedColor,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage -1 )
                            }
                        },
                        fraction = .43f
                    )
                }else{
                    ButtonBox(
                        text = "",
                        onClick = {

                        },
                        fraction = .43f
                    )
                }
                Spacer(Modifier.width(10.dp))
                ButtonBox(
                    text = buttonText[1],
                    color = if(pagerState.currentPage == state.quizList.size -1) optionUnSelectedColor else Color.Green,
                    onClick = {
                        if (pagerState.currentPage == state.quizList.size -1){
                            //navigate
                            navigateToResult(state.score,state.quizList.size)
                        }
                        scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1, animationSpec = spring())
                        }
                    },
                    fraction = 1f,
                )
            }
        }
    }

}
@Composable
fun ButtonBox(text : String,color : Color?=null , onClick : ()->Unit, fraction : Float = 1f) {

    Button(colors = ButtonDefaults.buttonColors(containerColor =color?: Color.Transparent ), onClick = {
        onClick()
    }, modifier = Modifier
        .fillMaxWidth(fraction)
        .height(54.dp)) {
        Text(text, fontSize = 16.sp)
    }
}
@Composable
fun QuizHeader(backToHome: () -> Unit, category: String) {
    Row {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable { backToHome() })
        Text(category, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun fetchQuiz(state: QuizStateScreen): Boolean {
    return when {
        state.isLoading -> {
            ShimmerEffect()
            false
        }

        state.quizList.isNotEmpty() -> {
            true
        }

        else -> {
            Text("${state.error}")
            false
        }
    }

}

@Preview
@Composable
private fun QuizScreenPreview() {
    QuizScreen(
        quizSize = 10,
        category = "Sports",
        difficulty = "Easy",
        type = "Multi",
        state = QuizStateScreen(),
        event = {},
        backToHome = { },
        navigateToResult = {score,sco->}
    )

}