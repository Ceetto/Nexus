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


sealed class Screen(val route: String){
    object Login : Screen("login")
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object List : Screen("list")
    object Friends : Screen("friends")
    object Profile : Screen("profile")
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

@ExperimentalAnimationApi
fun AnimatedContentScope<*>.defaultTiviEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
fun AnimatedContentScope<*>.defaultTiviExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
fun AnimatedContentScope<*>.defaultTiviPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
fun AnimatedContentScope<*>.defaultTiviPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}