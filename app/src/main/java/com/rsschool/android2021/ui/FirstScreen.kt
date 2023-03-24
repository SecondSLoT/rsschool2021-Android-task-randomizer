package com.rsschool.android2021.ui.firstscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsschool.android2021.uikit.DefaultButton

@Composable
fun FirstScreen(
    previousResult: State<Int>,
    minText: State<String>,
    onMinValueChange: (String) -> Unit,
    maxText: State<String>,
    onMaxValueChange: (String) -> Unit,
    isGenerateButtonEnabled: State<Boolean>,
    onGenerateButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Previous result: ${previousResult.value}",
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 80.dp),
        )
        DefaultGap()

        NumberTextField("Min", minText, onMinValueChange)
        NumberTextField("Max", maxText, onMaxValueChange)
        DefaultGap()

        DefaultButton(
            onClick = onGenerateButtonClick,
            text = "Generate",
            isEnabled = isGenerateButtonEnabled,
        )
    }
}

@Composable
private fun NumberTextField(
    label: String,
    text: State<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text.value,
        onValueChange = onValueChange,
        modifier = modifier.width(200.dp),
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
    )
}

@Composable
private fun DefaultGap() {
    Spacer(modifier = Modifier.padding(top = 80.dp))
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    FirstScreen(
        previousResult = remember { mutableStateOf(0) },
        minText = remember { mutableStateOf("") },
        onMinValueChange = {},
        maxText = remember { mutableStateOf("") },
        onMaxValueChange = {},
        isGenerateButtonEnabled = remember { mutableStateOf(true) },
        onGenerateButtonClick = {},
    )
}
