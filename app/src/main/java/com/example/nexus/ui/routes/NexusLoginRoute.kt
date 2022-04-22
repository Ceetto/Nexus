package com.example.nexus.ui.routes


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.viewmodels.NexusListViewModel
import com.example.nexus.viewmodels.NexusLoginViewModel
import com.example.nexus.viewmodels.UserState
import kotlinx.coroutines.launch

@Composable
fun NexusLoginRoute(vM: NexusLoginViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val vm = UserState.current
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
                TextField(
                    value = username,
                    onValueChange = { newText ->
                        username = newText
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    coroutineScope.launch {
                        vm.signIn(username, password)
                    }
                }, content = {
                    Text("Login")
                })
            }
        }
    }
}

