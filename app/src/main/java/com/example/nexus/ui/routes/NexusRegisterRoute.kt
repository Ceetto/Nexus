package com.example.nexus.ui.routes

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.loginComponents.*
import com.example.nexus.viewmodels.NexusLoginViewModel

@Composable
fun NexusRegisterRoute(vM: NexusLoginViewModel, navController: NavHostController) {
    Surface {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            UsernameTextField(
                { vM.getUsername() },
                { e -> vM.setUsername(e) },
                { vM.getIsUsernameValidTest() }
            )
            EmailTextField(
                { vM.getEmail() },
                { e -> vM.setEmail(e) },
                { vM.getIsEmailValidTest() },
                { vM.getUserAlreadyExists() },
                { vM.getUserDoesNotExists() }
            )
            PasswordTextField(
                { vM.getPassword() },
                { e -> vM.setPassword(e) },
                { vM.getIsPasswordValidTest() },
                { e -> vM.setIsPasswordVisible(e) },
                { vM.getIsPasswordVisible() }
            )
            Spacer(modifier = Modifier.height(70.dp))
            RegisterButton(
                { vM.createAccount() },
                { vM.checkEmail() },
                { vM.checkPassword() },
                { vM.getIsEmailValid() },
                { vM.getIsPasswordValid() },
                { vM.checkUsername() },
                { vM.getIsUsernameValid() }
            )
        }
    }
}