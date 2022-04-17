 package com.example.nexus.ui.routes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.example.nexus.viewmodels.NexusHomeViewModel


 @ExperimentalComposeUiApi
@Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel
) {
    Text(text="home")

}