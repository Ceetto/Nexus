package com.example.nexus


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.routes.LoginRoutes
import com.example.nexus.ui.routes.NexusLoginRoute
import com.example.nexus.ui.routes.NexusRegisterRoute
import com.example.nexus.viewmodels.NexusLoginViewModel

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ApplicationSwitcher(vM: NexusLoginViewModel) {
    if (vM.getIsLoggedIn()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Home(vM = hiltViewModel())
        }
    } else {
        ScreenMain()
    }
}

@Composable
fun ScreenMain(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginRoutes.Login.route) {

        composable(LoginRoutes.Login.route) {
            NexusLoginRoute(vM = hiltViewModel() ,navController = navController)
        }

        composable(LoginRoutes.Register.route) {
            NexusRegisterRoute(vM = hiltViewModel() ,navController = navController)
        }
    }
}