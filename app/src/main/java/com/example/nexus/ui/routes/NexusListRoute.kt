package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.nexus.viewmodels.NexusListViewModel

@Composable
fun NexusListRoute(
    vM: NexusListViewModel
){
    Text(text = "list")
}