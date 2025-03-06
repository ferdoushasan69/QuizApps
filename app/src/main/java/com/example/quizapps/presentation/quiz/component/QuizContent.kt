package com.example.quizapps.presentation.quiz.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapps.presentation.quiz.QuizState
import com.example.quizapps.ui.theme.borderColor
import com.example.quizapps.ui.theme.buttonColor
import com.example.quizapps.ui.theme.optionSelectedColor
import com.example.quizapps.ui.theme.optionUnSelectedColor
import com.example.quizapps.ui.theme.textFieldColor

@Composable
fun QuizContent(
    onQuizOptionClick: (Int) -> Unit,
    qNumber: Int,
    state: QuizState,
) {
    val question = state.quiz?.question!!.replace("&quot;", "\"").replace("&#039;", "\'")
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            "$qNumber .",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier ,
            fontSize = 17.sp,
        )
        Text(
            question,
            color = MaterialTheme.colorScheme.onPrimary
        )


    }
    Column(
        modifier = Modifier.fillMaxSize(),

    ) {

        val quizOptions = listOf(
            "A" to state.shuffleOptions[0].replace("&quot;", "\"").replace("&#039;", "\'"),
            "B" to state.shuffleOptions[1].replace("&quot;", "\"").replace("&#039;", "\'"),
            "C" to state.shuffleOptions[2].replace("&quot;", "\"").replace("&#039;", "\'"),
            "D" to state.shuffleOptions[3].replace("&quot;", "\"").replace("&#039;", "\'")
        )
        Spacer(Modifier.height(16.dp))
            quizOptions.forEachIndexed { index, (optionNumber, optionText) ->
                val isCorrect = when{
                    state.selectedOptions == index->
                        state.quiz.correct_answer == state.shuffleOptions[index]
                    state.selectedOptions != -1 && state.quiz.correct_answer == state.shuffleOptions[index]-> true
                    else -> {null}
                }
                val alwaysShowCorrect = state.selectedOptions != -1 && state.quiz.correct_answer == state.shuffleOptions[index]

                QuizOption(
                    optionText = optionText,
                    optionNumber = optionNumber,
                    selected = state.selectedOptions == index,
                    onSelectedOptionClick = {
                        onQuizOptionClick(index)
                    },
                    onUnSelectedOption = {
                        onQuizOptionClick(-1)
                    },
                    isCorrect = if (alwaysShowCorrect) true else isCorrect,
                    isDisabled = state.selectedOptions !=-1,
                )
                Spacer(Modifier.height(20.dp))
            }
    }
}

@Composable
fun QuizOption(
    optionText: String,
    optionNumber: String,
    selected: Boolean,
    isCorrect : Boolean?,
    isDisabled : Boolean,
    onSelectedOptionClick: () -> Unit,
    onUnSelectedOption: () -> Unit

) {
    val buttonColor by animateColorAsState(
        targetValue =when{
            isCorrect == true -> buttonColor
            isCorrect==false -> borderColor
            else-> textFieldColor
        },
        animationSpec = tween(500, easing = FastOutSlowInEasing),
    )

    var selectedOptions by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(buttonColor)
        .clickable(
            enabled = !isDisabled,
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onSelectedOptionClick()

        }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!selected) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .weight(1.5f)
                        .clip(CircleShape)
                        .background(buttonColor), contentAlignment = Alignment.Center
                ) {
                    Text(
                        optionNumber,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .weight(1.5f)
                        .clip(CircleShape)
                        .background(buttonColor), contentAlignment = Alignment.Center
                ) {
                }
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = optionText,
                modifier = Modifier
                    .weight(7.1f),
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 3
            )

            if (selected) {

                    Icon(
                      if (isCorrect == true) Icons.Default.CheckCircle else Icons.Default.Clear,
                        contentDescription = null,
                        tint = if(isCorrect==true) Color.White else Color.White
                    )

            }
        }
    }

}
