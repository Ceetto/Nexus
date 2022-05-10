package com.example.nexus.ui.components.loginComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun UsernameTextField(
    getUsername : () -> String,
    setUsername : (String) -> Unit
) {
    val focusManager =  LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = getUsername(),
            label = { Text("Username") },
            onValueChange = { newText ->
                setUsername(newText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            trailingIcon = {
                if (getUsername().isNotBlank()) {
                    IconButton(onClick = { setUsername("") }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear email"
                        )
                    }
                }
            }
        )
    }
}