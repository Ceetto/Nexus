package com.example.nexus.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.nexus.ui.navigation.Screen
import com.example.nexus.ui.theme.NexusBlack

@Composable
fun NexusTopBar(
    navController: NavHostController,
    canPop: Boolean,
    focusManager: FocusManager
){
    val onSettingsClick = {
        navController.navigate(Screen.Settings.route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
    val onProfileClick = {
        navController.navigate(Screen.Profile.route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
    if(canPop){
        TopAppBar(
            title = { Text("nexus") },
            backgroundColor = MaterialTheme.colors.primary,
            navigationIcon = {
                IconButton(onClick = {
                    focusManager.clearFocus()
                    navController.navigateUp()
                }) {
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
            backgroundColor = MaterialTheme.colors.primary,
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