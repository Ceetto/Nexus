package com.example.nexus.ui.routes


import android.util.Log
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
import com.example.nexus.activities.MainActivity.Companion.TAG
import com.example.nexus.viewmodels.NexusLoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun NexusLoginRoute(vM: NexusLoginViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager =  LocalFocusManager.current
    val showDialogState : Boolean by vM.showDialog.collectAsState()
    val auth by lazy {
        Firebase.auth
    }

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
                OutlinedTextField(
                    value = vM.getEmail(),
                    label = { Text("Email Address") },
                    placeholder = { Text("abc@domain.com") },
                    onValueChange = { newText ->
                        vM.setEmail(newText) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions (
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    isError = !vM.getIsEmailValid(),
                    trailingIcon = {
                        if (vM.getEmail().isNotBlank()) {
                            IconButton(onClick = { vM.setEmail("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear email"
                                )
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = vM.getPassword(),
                    label = { Text("Password") },
                    onValueChange = { newText ->
                        vM.setPassword(newText) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions (
                        onNext = { focusManager.clearFocus() }
                    ),
                    isError = !vM.getIsPasswordValid(),
                    trailingIcon = {
                        IconButton(onClick = { vM.setIsPasswordVisible(!vM.getIsPasswordVisible()) }) {
                            Icon(imageVector = if(vM.getIsPasswordVisible()) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility")
                        }
                    },
                    visualTransformation = if(vM.getIsPasswordVisible()) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(70.dp))
                OutlinedButton(onClick = {
                    auth.signInWithEmailAndPassword(vM.getEmail(), vM.getPassword())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                coroutineScope.launch {
                                    vM.signIn(vM.getEmail(), vM.getPassword())
                                }
                                Log.d(TAG, "The user has successfully logged in")
                                val user = auth.currentUser
                                if (user != null) {
                                    vM.setUserId(user.uid)
                                }
                            } else {
                                Log.w(TAG, "The user has FAILED to log in", it.exception)
                                vM.onOpenDialogClicked()
                            }
                        }
                    }, content = {
                        Text("Login")
                    }, enabled = vM.getIsPasswordValid() && vM.getIsEmailValid()
                    , modifier = Modifier
                        .height(40.dp)
                        .width(225.dp)
                    , shape = RoundedCornerShape(50)
                    , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xffa5dee5))
                    , border = BorderStroke(1.dp, Color(0xffa5dee5))
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    auth.createUserWithEmailAndPassword(vM.getEmail(), vM.getPassword())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                coroutineScope.launch {
                                    vM.signIn(vM.getEmail(), vM.getPassword())
                                }
                                Log.d(TAG, "The user registered a new account and logged in")
                            } else {
                                Log.w(TAG, "The user has FAILED to make a new account and log in", it.exception)
                            }
                        }
                    }, content = {
                    Text("Register")
                    }, modifier = Modifier
                    .height(40.dp)
                    .width(225.dp)
                    , shape = RoundedCornerShape(50)
                    , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xff84b2b7))
                    , border = BorderStroke(1.dp, Color(0xff84b2b7))
                )
                SimpleAlertDialog(
                    show = showDialogState,
                    onDismiss = vM::onDialogDismiss,
                    onConfirm = vM::onDialogConfirm,
                    text = "User doesn't exists try to register first"
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