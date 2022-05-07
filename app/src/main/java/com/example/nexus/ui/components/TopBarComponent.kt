package com.example.nexus.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.nexus.ui.navigation.Screen
import com.example.nexus.ui.theme.NexusBlack

@Composable
fun NexusTopBar(
    navController: NavHostController,
    canPop: Boolean
){
    val onSettingsClick = {
        while(navController.navigateUp()){
            navController.navigateUp()
        }
        navController.navigate(Screen.Settings.route) {
        launchSingleTop = true
        restoreState = true

        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }}
    val onProfileClick = {
        while(navController.navigateUp()){
            navController.navigateUp()
        }
        navController.navigate(Screen.Profile.route) {
        launchSingleTop = true
        restoreState = true

        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
    }}
    if(canPop){
        TopAppBar(
            title = { Text("nexus") },
            backgroundColor = NexusBlack,
            navigationIcon = {
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            },
            actions = {
                IconButton(onClick = onProfileClick){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "profile"
                    )
                }
                IconButton (onClick = onSettingsClick){
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings"
                    )
                }
            }
        )
    } else {
        TopAppBar(
            title = { Text("nexus") },
            backgroundColor = NexusBlack,
            actions = {
                IconButton(onClick = onProfileClick){
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "profile"
                    )
                }
                IconButton (onClick = onSettingsClick){
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "settings"
                    )
                }
            }
        )
    }

}