package com.example.nexus.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.*
import com.example.nexus.ui.routes.*
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.ui.routes.list.NexusListRoute


sealed class Screen(val route: String){
    object Login : Screen("login")
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object List : Screen("list")
    object Friends : Screen("friends")
    object Profile : Screen("profile")
    object Search: Screen("search")
}

sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun NexusNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ){
        addLoginScreen(navController)
        addHomeScreen(navController)
        addNotificationsScreen(navController)
        addListScreen(navController)
        addFriendsScreen(navController)
        addProfileScreen(navController)
        addSearchScreen(navController)
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addSearchScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Search.route
    ){
        NexusSearchRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addLoginScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Login.route,
    ){
        NexusLoginRoute(vM = hiltViewModel())
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addHomeScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Home.route,
    ){
        NexusHomeRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addNotificationsScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Notifications.route,
    ){
        NexusNotificationsRoute(vM = hiltViewModel())
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addListScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.List.route,
    ){
        NexusListRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addFriendsScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Friends.route,
    ){
        NexusFriendsRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addProfileScreen(
    navController: NavHostController,
){
    composable(
        route = Screen.Profile.route,
    ){
        NexusProfileRoute(vM = hiltViewModel())
    }
}

val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph
