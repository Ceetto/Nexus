package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusNotificationsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusNotificationsRoute(
    vM: NexusNotificationsViewModel,
    navController: NavHostController
){
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false) }
    ) {
        Text(text = "notifications")
    }

}