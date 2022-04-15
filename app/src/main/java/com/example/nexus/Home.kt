package com.example.nexus

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.navigation.NexusNavGraph
import com.example.nexus.ui.navigation.Screen

@ExperimentalAnimationApi
@Composable
fun Home(
){
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        val currentSelectedItem by navController.currentScreenAsState()
        BottomNavigationBar(selectedNavigation = currentSelectedItem,
            onNavigationSelected = { selected ->
                navController.navigate(selected.route) {
                    launchSingleTop = true
                    restoreState = true

                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            },
            modifier = Modifier.fillMaxHeight(),
        )
    }){
        Row(modifier = Modifier
            .fillMaxSize()){
            NexusNavGraph(navController)
        }
    }
}

//TODO: add colors to ui.theme files, also new icon for friend page
@Composable
fun BottomNavigationBar(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
){
    BottomNavigation(
        elevation = 8.dp, backgroundColor = Color.DarkGray
    ) {
        BottomNavigationItem(
            selected = (selectedNavigation == Screen.Home),
            onClick = {
                onNavigationSelected(Screen.Home)
            },
            icon = { Icon(imageVector = Icons.Default.Home, "Home") },
        )

        BottomNavigationItem(
            selected = (selectedNavigation == Screen.Notifications),
            onClick = {
                onNavigationSelected(Screen.Notifications)
            },
            icon = { Icon(imageVector = Icons.Default.Notifications, "Notifications") },
        )

        BottomNavigationItem(
            selected = (selectedNavigation == Screen.List),
            onClick = {
                onNavigationSelected(Screen.List)
            },
            icon = { Icon(imageVector = Icons.Default.List, "List") },
        )

        BottomNavigationItem(
            selected = (selectedNavigation == Screen.Friends),
            onClick = {
                onNavigationSelected(Screen.Friends)
            },
            icon = { Icon(imageVector = Icons.Default.AccountCircle, "Friends") },
        )

        BottomNavigationItem(
            selected = (selectedNavigation == Screen.Profile),
            onClick = {
                onNavigationSelected(Screen.Profile)
            },
            icon = { Icon(imageVector = Icons.Default.Person, "Profile") },
        )
    }
}


/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Login) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Login.route } -> {
                    selectedItem.value = Screen.Login
                }
                destination.hierarchy.any { it.route == Screen.Home.route } -> {
                    selectedItem.value = Screen.Home
                }
                destination.hierarchy.any { it.route == Screen.Notifications.route } -> {
                    selectedItem.value = Screen.Notifications
                }
                destination.hierarchy.any { it.route == Screen.List.route } -> {
                    selectedItem.value = Screen.List
                }
                destination.hierarchy.any { it.route == Screen.Friends.route } -> {
                    selectedItem.value = Screen.Friends
                }
                destination.hierarchy.any { it.route == Screen.Profile.route } -> {
                    selectedItem.value = Screen.Profile
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
