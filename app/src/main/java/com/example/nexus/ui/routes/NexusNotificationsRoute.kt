package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.nexus.viewmodels.NexusNotificationsViewModel

@Composable
fun NexusNotificationsRoute(
    vM: NexusNotificationsViewModel
){
    Text(text = "notifications")
}