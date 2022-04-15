 package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.nexus.viewmodels.NexusHomeViewModel

@Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel
){
    Text(text = "home")
}