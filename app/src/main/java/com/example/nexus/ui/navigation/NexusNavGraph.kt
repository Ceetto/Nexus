package com.example.nexus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.routes.*

sealed class Screen(val route: String){
    object Login : Screen("login")
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object List : Screen("list")
    object Friends : Screen("friends")
    object Profile : Screen("profile")
}



@Composable
fun NexusNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
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