package com.example.quizapps.presentation.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quizapps.ui.theme.bgColor
import com.example.quizapps.ui.theme.borderColor
import com.example.quizapps.ui.theme.optionUnSelectedColor
import com.example.quizapps.ui.theme.primaryButtonColor
import com.example.quizapps.ui.theme.textFieldColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDropDownMenu(
    modifier: Modifier = Modifier,
    menuList: List<String>,
    menuTitle: String,
    text: String,
    onMenuItemClick: (String) -> Unit
) {
    var isExpand by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(10.dp)

    ){
        Text(
            menuTitle,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        ExposedDropdownMenuBox(
            expanded = isExpand,
            onExpandedChange = { isExpand = !isExpand }
        ) {
            TextField(
                readOnly = true,
                onValueChange = {}, value = text,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = textFieldColor,
                    unfocusedContainerColor = textFieldColor,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .height(60.dp)
                    .clip(RoundedCornerShape(16.dp)),
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
            DropdownMenu(
                expanded = isExpand,
                onDismissRequest = {
                    isExpand = false
                },
                modifier = Modifier
                    .background(bgColor)
                    .padding(6.dp)
            ) {
                menuList.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.White) },
                        onClick = {
                            onMenuItemClick(menuList[index])
                            isExpand = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding

                    )
                }
            }


        }
    }

}