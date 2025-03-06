package com.example.quizapps.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapps.presentation.component.AppBarDropDownMenu
import com.example.quizapps.presentation.component.HomeHeader
import com.example.quizapps.ui.theme.bgColor
import com.example.quizapps.ui.theme.borderColor
import com.example.quizapps.ui.theme.buttonColor
import com.example.quizapps.ui.theme.primaryButtonColor
import com.example.quizapps.utils.K
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("ContextCastToActivity")
@Composable
fun HomeScreen(
    state: HomeState,
    event : (HomeScreenEvent)->Unit,
    onGenerateQuizClick : (Int,String,String,String)->Unit
) {
    val currentActivity = LocalContext.current as? Activity
    BackHandler {
        currentActivity?.finish()
    }


    Scaffold  {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(bgColor)
        ) {

            HomeHeader()
            AppBarDropDownMenu(
                menuList = K.numberAsString,
                onMenuItemClick = {
                    event(HomeScreenEvent.SetNumberOfQuiz(it.toInt()))
                },
                menuTitle = "Number of Quiz : ",
                text = state.quizSize.toString()
            )
            AppBarDropDownMenu(
                menuList = K.categories,
                onMenuItemClick = {
                    event(HomeScreenEvent.SetCategoryOfQuiz(it))
                },
                menuTitle = "Select Category",
                text = state.category
            )
            AppBarDropDownMenu(
                menuList = K.difficulty,
                onMenuItemClick = {
                    event(HomeScreenEvent.SetDifficultyOfQuiz(it))
                },
                menuTitle = "Select Difficulty : ",
                text = state.difficulty
            )
            AppBarDropDownMenu(
                menuList = K.type,
                onMenuItemClick = {
                    event(HomeScreenEvent.SetTypesOfQuiz(it))
                },
                menuTitle = "Select Types : ",
                text = state.type
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    onGenerateQuizClick(
                        state.quizSize,
                        state.category,
                        state.difficulty,
                        state.type
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryButtonColor
                )
            ) {
                Text("Generate Quiz", fontSize = 18.sp)
            }
        }
    }
    }

//@Preview
//@Composable
//private fun HomeScreenPreview() {
//    HomeScreen(
//        state = HomeState(),
//        event = {},
//        onGenerateQuizClick =
//    )
//}