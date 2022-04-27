package com.example.nexus.ui.routes


import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.activities.MainActivity.Companion.TAG
import com.example.nexus.viewmodels.NexusLoginViewModel
import com.example.nexus.viewmodels.UserState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun NexusLoginRoute(vM: NexusLoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val focusManager =  LocalFocusManager.current
    val vm = UserState.current
    val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    val isPasswordValid by derivedStateOf {
        password.length > 7
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
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
            if (vm.isBusy) {
                CircularProgressIndicator()
            } else {
                Text("Login Screen", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    label = { Text("Email Address") },
                    placeholder = { Text("abc@domain.com") },
                    onValueChange = { newText ->
                        email = newText },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions (
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    isError = !isEmailValid,
                    trailingIcon = {
                        if (email.isNotBlank()) {
                            IconButton(onClick = { email = "" }) {
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
                    value = password,
                    label = { Text("Password") },
                    onValueChange = { newText ->
                        password = newText },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions (
                        onNext = { focusManager.clearFocus() }
                    ),
                    isError = !isPasswordValid,
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordValid }) {
                            Icon(imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility")
                        }
                    },
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                coroutineScope.launch {
                                    vm.signIn(email, password)
                                }
                                Log.d(TAG, "The user has successfully logged in")
                            } else {
                                Log.w(TAG, "The user has FAILED to log in", it.exception)
                            }
                        }
                }, content = {
                    Text("Login")
                })
                OutlinedButton(onClick = { /*TODO*/ },
                    content = {
                        Text("Forgot Password?")
                    }, enabled = isEmailValid && isPasswordValid
                )
                OutlinedButton(onClick = {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                coroutineScope.launch {
                                    vm.signIn(email, password)
                                }
                                Log.d(TAG, "The user registered a new account and logged in")
                            } else {
                                Log.w(TAG, "The user has FAILED to make a new account and log in", it.exception)
                            }
                        }
                }, content = {
                    Text("Register")
                })
            }
        }
    }
}

