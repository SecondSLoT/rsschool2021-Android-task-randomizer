package com.rsschool.android2021.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsschool.android2021.uikit.DefaultButton

@Composable
fun SecondScreen(
    result: String,
    onBackButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Result:",
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 80.dp),
        )
        Text(
            text = result,
            fontSize = 66.sp,
            modifier = Modifier.padding(top = 50.dp),
        )
        DefaultButton(
            onClick = onBackButtonClick,
            text = "Back",
            modifier = Modifier.padding(top = 80.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    SecondScreen(
        result = "54",
        onBackButtonClick = {},
    )
}
