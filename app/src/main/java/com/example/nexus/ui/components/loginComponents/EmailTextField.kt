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
    getIsEmailValidTest: () -> Boolean,
    getUserAlreadyExists: () -> Boolean,
    getUserDoesNotExists: () -> Boolean
) {
    val focusManager =  LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = getEmail(),
            label = { Text("Email Address") },
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
            ErrorMessage(text = "Incorrect email address")
        } else if (getUserAlreadyExists()) {
            ErrorMessage(text = "Email already in use")
        } else if (getUserDoesNotExists()) {
            ErrorMessage(text = "Email does not exist")
        }
    }
}