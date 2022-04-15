package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.nexus.viewmodels.NexusProfileViewModel

@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel
){
    Text(text = "profile")
}