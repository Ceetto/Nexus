package com.example.nexus.ui.components.loginComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    getPassword: () -> String,
    setPassword: (String) -> Unit,
    getIsPasswordValid: () -> Boolean,
    setIsPasswordVisible: (Boolean) -> Unit,
    getIsPasswordVisible: () -> Boolean
) {
    val focusManager =  LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = getPassword(),
            label = { Text("Password") },
            onValueChange = { newText ->
                setPassword(newText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions (
                onNext = { focusManager.clearFocus() }
            ),
            isError = !getIsPasswordValid(),
            trailingIcon = {
                IconButton(onClick = { setIsPasswordVisible(!getIsPasswordVisible()) }) {
                    Icon(imageVector = if(getIsPasswordVisible()) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility")
                }
            },
            visualTransformation = if(getIsPasswordVisible()) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (!getIsPasswordValid()) {
            Text(
                text = "Incorrect password",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}