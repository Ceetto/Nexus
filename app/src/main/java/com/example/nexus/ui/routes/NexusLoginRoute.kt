package com.example.nexus.ui.routes


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.viewmodels.NexusLoginViewModel

@Composable
fun NexusLoginRoute(vM: NexusLoginViewModel) {
    val showDialogState : Boolean by vM.showDialog.collectAsState()

    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (vM.getIsBusy()) {
                CircularProgressIndicator()
            } else {
                Logo()
                EmailTextField(
                    { vM.getEmail() },
                    { e -> vM.setEmail(e) },
                    { vM.getIsEmailValid() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordTextField(
                    { vM.getPassword() },
                    { e -> vM.setPassword(e) },
                    { vM.getIsPasswordValid() },
                    { e -> vM.setIsPasswordVisible(e) },
                    { vM.getIsPasswordVisible() }
                )
                Spacer(modifier = Modifier.height(70.dp))
                LoginButton(
                    { vM.getIsEmailValid() },
                    { vM.getIsPasswordValid() },
                    { vM.signIn() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                RegisterButton { vM.createAccount() }
                SimpleAlertDialog(
                    show = showDialogState,
                    onDismiss = vM::onDialogDismiss,
                    onConfirm = vM::onDialogConfirm,
                    text = "Login has failed try again"
                )
            }
        }
    }
}

@Composable
fun Logo() {
    Image(
        painter = rememberAsyncImagePainter(R.drawable.logo),
        contentDescription = "login logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(185.dp)
    )
}

@Composable
fun EmailTextField(
    getEmail: () -> String,
    setEmail: (String) -> Unit,
    getIsEmailValid: () -> Boolean
) {
    val focusManager =  LocalFocusManager.current

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
        isError = !getIsEmailValid(),
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
}

@Composable
fun PasswordTextField(
    getPassword: () -> String,
    setPassword: (String) -> Unit,
    getIsPasswordValid: () -> Boolean,
    setIsPasswordVisible: (Boolean) -> Unit,
    getIsPasswordVisible: () -> Boolean
) {
    val focusManager =  LocalFocusManager.current

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
        isError = getIsPasswordValid(),
        trailingIcon = {
            IconButton(onClick = { setIsPasswordVisible(!getIsPasswordVisible()) }) {
                Icon(imageVector = if(getIsPasswordVisible()) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password visibility")
            }
        },
        visualTransformation = if(getIsPasswordVisible()) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(
    getIsEmailValid: () -> Boolean,
    getIsPasswordValid: () -> Boolean,
    signIn: () -> Unit
) {
    OutlinedButton(onClick = {
        signIn()
    }, content = {
        Text("Login")
    }, enabled = getIsPasswordValid() && getIsEmailValid()
        , modifier = Modifier
            .height(40.dp)
            .width(225.dp)
        , shape = RoundedCornerShape(50)
        , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xffa5dee5))
        , border = BorderStroke(1.dp, Color(0xffa5dee5))
    )
}

@Composable
fun RegisterButton(
    createAccount: () -> Unit
) {
    OutlinedButton(onClick = {
        createAccount()
    }, content = {
        Text("Register")
    }, modifier = Modifier
        .height(40.dp)
        .width(225.dp)
        , shape = RoundedCornerShape(50)
        , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xff84b2b7))
        , border = BorderStroke(1.dp, Color(0xff84b2b7))
    )
}

@Composable
fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    text: String
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = "Cancel") }
            },
            title = { Text(text = "Login Failed") },
            text = { Text(text = text) }
        )
    }
}