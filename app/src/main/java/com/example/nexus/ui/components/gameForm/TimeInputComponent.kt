package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TimeInput(
    focusManager: FocusManager,
    text: String,
    getTime: () -> String,
    setTime: (time: String) -> Unit,
    weight1: Float,
    weight2: Float
){
    Row(modifier = Modifier
        .padding(5.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }){
        Text(text = text, modifier = Modifier.padding(top = 20.dp).weight(weight1, true))
        TextField(
            value = getTime(),
            onValueChange = { value ->
                setTime(value)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier.weight(weight2, true)
        )
    }
}