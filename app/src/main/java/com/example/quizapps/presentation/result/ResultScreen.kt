package com.example.quizapps.presentation.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapps.R
import com.example.quizapps.presentation.quiz.ButtonBox
import com.example.quizapps.ui.theme.bgColor
import com.example.quizapps.ui.theme.optionSelectedColor

@Composable
fun ResultScreen(totalScore: Int, quizSize: Int,backHome:()->Unit) {
    BackHandler {
        backHome()
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.trophy),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Text(
            "Congratulations",
            fontSize = 33.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Total Score : $totalScore out of $quizSize",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        ButtonBox(
            text = "Back to Home",
            color = optionSelectedColor,
            onClick = {
                backHome()
            },
            fraction = 1f,
        )
    }

}