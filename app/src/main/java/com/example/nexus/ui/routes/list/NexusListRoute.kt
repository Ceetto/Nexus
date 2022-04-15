package com.example.nexus.ui.routes.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.navigation.ListNavGraph
import com.example.nexus.ui.navigation.ListScreen
import com.example.nexus.viewmodels.NexusListViewModel

@ExperimentalAnimationApi
@Composable
fun NexusListRoute(
    vM: NexusListViewModel
){
    val navController = rememberNavController()
    Scaffold(topBar = {
        val currentSelectedItem by navController.currentScreenAsState()
        TopNavigationBar(selectedNavigation = currentSelectedItem,
            onNavigationSelected = { selected ->
                navController.navigate(selected.route) {
                    launchSingleTop = true
                    restoreState = true

                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                }
            }
        )
    }) {
        ListNavGraph(navController)
    }


}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<ListScreen> {
    val selectedItem = remember { mutableStateOf<ListScreen>(ListScreen.All) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == ListScreen.All.route } -> {
                    selectedItem.value = ListScreen.All
                }
                destination.hierarchy.any { it.route == ListScreen.Playing.route } -> {
                    selectedItem.value = ListScreen.Playing
                }
                destination.hierarchy.any { it.route == ListScreen.Completed.route } -> {
                    selectedItem.value = ListScreen.Completed
                }
                destination.hierarchy.any { it.route == ListScreen.Planned.route } -> {
                    selectedItem.value = ListScreen.Planned
                }
                destination.hierarchy.any { it.route == ListScreen.Dropped.route } -> {
                    selectedItem.value = ListScreen.Dropped
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

@Composable
fun TopNavigationBar(
    selectedNavigation: ListScreen,
    onNavigationSelected: (ListScreen) -> Unit
){
    Row(modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .fillMaxWidth()
        .height(60.dp)) {
        ListNavigationItems.forEach {item ->
            TextButton(
                onClick = {
                    onNavigationSelected(item.screen)
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = item.tabValue,
                    style = MaterialTheme.typography.body2,
                    color= MaterialTheme.colors.primary,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 25.sp)

            }
        }
    }
}

private class ListNavigationItem(
    val screen: ListScreen,
    val tabValue: String,
)

private val ListNavigationItems = listOf(
    ListNavigationItem(ListScreen.All, ListCategory.ALL.value),
    ListNavigationItem(ListScreen.Playing, ListCategory.PLAYING.value),
    ListNavigationItem(ListScreen.Completed, ListCategory.COMPLETED.value),
    ListNavigationItem(ListScreen.Planned, ListCategory.PLANNED.value),
    ListNavigationItem(ListScreen.Dropped, ListCategory.DROPPED.value),
)

enum class ListCategory(val value: String){
    ALL("all"),
    PLAYING("playing"),
    COMPLETED("completed"),
    PLANNED("planned"),
    DROPPED("dropped")
}