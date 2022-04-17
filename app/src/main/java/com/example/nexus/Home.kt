package com.example.nexus

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.navigation.NexusNavGraph
import com.example.nexus.ui.navigation.Screen
import com.google.accompanist.navigation.animation.*

@ExperimentalComposeUiApi
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
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        )
    }){
        Row(Modifier.fillMaxSize()) {
            NexusNavGraph(navController, modifier = Modifier.fillMaxSize().weight(1f).padding(0.dp, 0.dp, 0.dp, 65.dp))
        }
    }
}

//TODO: add colors to ui.theme files, also new icon for friend page
@Composable
fun BottomNavigationBar(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier,
){
    BottomNavigation(
        elevation = 16.dp, backgroundColor = Color(25, 25, 25, 150),
        modifier = modifier
    ) {
        HomeNavigationItems.forEach {item ->
            BottomNavigationItem(
                selected = (selectedNavigation == item.screen),
                onClick = {
                    onNavigationSelected(item.screen)
                },
                icon = { Icon(imageVector = item.icon, item.screen.route,
                    modifier = Modifier.fillMaxSize(0.6f), tint = Color.White)},
//                label = { Text(text= item.label, fontSize = 9.sp, overflow = TextOverflow.Clip)},
                modifier = Modifier.fillMaxSize()
            )
        }
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

private class HomeNavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

private val HomeNavigationItems = listOf(
    HomeNavigationItem(Screen.Home, Icons.Default.Home, "Home"),
    HomeNavigationItem(Screen.Notifications, Icons.Default.Notifications, "Notification"),
    HomeNavigationItem(Screen.List, Icons.Default.List, "My List"),
    HomeNavigationItem(Screen.Friends, Icons.Default.AccountCircle, "Friends"),
    HomeNavigationItem(Screen.Profile, Icons.Default.Person, "Profile"),
)
