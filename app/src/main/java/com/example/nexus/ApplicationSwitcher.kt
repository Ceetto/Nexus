package com.example.nexus

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexus.ui.routes.NexusLoginRoute
import com.example.nexus.viewmodels.UserState

@OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun ApplicationSwitcher() {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Home()
        }
    } else {
        NexusLoginRoute(vM = hiltViewModel())
    }
}