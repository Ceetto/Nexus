package com.example.nexus.ui.components.loginComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    getEmail: () -> String,
    setEmail: (String) -> Unit,
    getIsEmailValid: () -> Boolean,
    getIsEmailValidTest: () -> Boolean
) {
    val focusManager =  LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = getEmail(),
            label = { Text("Email Address") },
            placeholder = { Text("abc@domain.com") },
            onValueChange = { newText ->
                setEmail(newText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            isError = !getIsEmailValidTest(),
            trailingIcon = {
                if (getEmail().isNotBlank()) {
                    IconButton(onClick = { setEmail("") }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear email"
                        )
                    }
                }
            }
        )
        if (!getIsEmailValidTest()) {
            Text(
                text = "Incorrect email address",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}