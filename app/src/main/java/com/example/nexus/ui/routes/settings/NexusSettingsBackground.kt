package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusSettingsBackground(vM: NexusProfileViewModel, navController: NavHostController){
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true) }
    ) {
        Scaffold(
            topBar = { NexusTopBar(navController = navController, canPop = true) }
        ) {
            Row() {
                Text(text = "Current Background: ")
            }

            Row(){

            }

            Row() {
                Text(text = "New Background: ")
            }
            Row(){

            }

        }
    }
}