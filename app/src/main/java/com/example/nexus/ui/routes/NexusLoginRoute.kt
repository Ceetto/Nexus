package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.nexus.viewmodels.NexusLoginViewModel

@Composable
fun NexusLoginRoute(
    vM: NexusLoginViewModel
){
    Text(text = "login")
}