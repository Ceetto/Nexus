package com.example.nexus.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nexus.ui.routes.list.*

sealed class ListScreen(val route: String){
    object All : ListScreen("all")
    object Playing : ListScreen("playing")
    object Completed : ListScreen("completed")
    object Planned : ListScreen("planned")
    object Dropped : ListScreen("dropped")
}

@ExperimentalAnimationApi
@Composable
fun ListNavGraph(
    navController: NavHostController,
    startDestination: String = ListScreen.All.route,
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        addAll(navController)
        addPlaying(navController)
        addCompleted(navController)
        addPlanned(navController)
        addDropped(navController)
    }
}

private fun NavGraphBuilder.addAll(
    navController: NavHostController
){
    composable(
        route = ListScreen.All.route
    ) {
        AllRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addPlaying(
    navController: NavHostController
){
    composable(
        route = ListScreen.Playing.route
    ) {
        PlayingRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addCompleted(
    navController: NavHostController
){
    composable(
        route = ListScreen.Completed.route
    ) {
        CompletedRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addPlanned(
    navController: NavHostController
){
    composable(
        route = ListScreen.Planned.route
    ) {
        PlannedRoute(vM = hiltViewModel())
    }
}

private fun NavGraphBuilder.addDropped(
    navController: NavHostController
){
    composable(
        route = ListScreen.Dropped.route
    ) {
        DroppedRoute(vM = hiltViewModel())
    }
}