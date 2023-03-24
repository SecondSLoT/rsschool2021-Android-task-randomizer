package com.rsschool.android2021.uikit

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DefaultButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: State<Boolean> = mutableStateOf(true),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled.value,
    ) {
        Text(text = text.uppercase())
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultButtonPreview() {
    DefaultButton(
        onClick = {},
        text = "Generate",
        isEnabled = remember { mutableStateOf(true) },
    )
}
