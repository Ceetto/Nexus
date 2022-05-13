package com.example.nexus

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.navigation.Screen
import com.example.nexus.ui.routes.LoginRoutes
import com.example.nexus.ui.routes.NexusLoginRoute
import com.example.nexus.ui.routes.NexusRegisterRoute
import com.example.nexus.viewmodels.NexusLoginViewModel

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ApplicationSwitcher(vM: NexusLoginViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        createNotificationChannel("Nexus", context)
    }

    if (vM.getIsLoggedIn()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Home()
        }
    } else {
        ScreenMain()
    }
}

fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Nexus"
        val desc = "Your gaming list"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = desc
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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