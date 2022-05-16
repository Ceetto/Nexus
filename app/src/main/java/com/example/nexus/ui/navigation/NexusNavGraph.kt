package com.example.nexus.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nexus.ui.routes.*
import com.example.nexus.ui.routes.lists.NexusFriendListRoute
import com.example.nexus.ui.routes.lists.NexusListRoute
import com.example.nexus.ui.routes.profiles.NexusFriendProfileRoute
import com.example.nexus.ui.routes.profiles.NexusProfileRoute
import com.example.nexus.ui.routes.search.NexusGameDetailRoute
import com.example.nexus.ui.routes.search.NexusSearchRoute
import com.example.nexus.ui.routes.settings.NexusSettingsBackground
import com.example.nexus.ui.routes.settings.NexusSettingsPicture
import com.example.nexus.ui.routes.settings.NexusSettingsRoute
import com.example.nexus.ui.routes.settings.NexusSettingsUsername


sealed class Screen(open val route: String){
    object Login : Screen("login")
    object Home : Screen("home")
    object Notifications : Screen("notifications")
    object List : Screen("list")
    object Friends : Screen("friends")
    object Profile : Screen("profile")
    object Search: Screen("search")
    object Settings: Screen("settings")
}

sealed class LeafScreen(
    override val route: String
) : Screen(route) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object GameDetail : LeafScreen("game/{gameId}"){
        fun createRoute(root : Screen, gameId: Long) : String {
            return "${root.route}/game/$gameId"
        }
    }

    object FriendScreen : LeafScreen("profile/{userId}"){
        fun createRoute(root: Screen, userId: String) : String {
            return "${root.route}/profile/$userId"
        }
    }


    object ListScreen : LeafScreen("list/{userId}"){
        fun createRoute(root: Screen, userId: String) : String {
            return "${root.route}/list/$userId"
        }
    }


//    object GameForm : LeafScreen("game/{gameId}/{gameName}"){
//        fun createRoute(root: Screen, gameId : Long, gameName: String) : String{
//            return "${root.route}/game/$gameId/$gameName"
//        }
//    }

    object SettingsPages : LeafScreen("setting/{settingsPage}"){
        fun createRoute(root : Screen, settingsPage: String) : String {
            return "${root.route}/setting/$settingsPage"
        }
    }

    object Notifications: LeafScreen("notifications")
    object Register : LeafScreen("register")
    object Login : LeafScreen("login")
    object Settings : LeafScreen("settings")
    object Profile : LeafScreen("profile")
    object Search : LeafScreen("search")
    object Home : LeafScreen("home")
    object List : LeafScreen("list")
    object Friends : LeafScreen("friends")
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable fun NexusNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ){
        addLoginScreenTopLevel(navController)
        addNotificationsTopLevel(navController)
        addListScreenTopLevel(navController)
        addFriendsScreenTopLevel(navController)
        addProfileScreenTopLevel(navController)
        addSearchScreenTopLevel(navController)
        addSettingsScreenTopLevel(navController)
        addHomeScreenTopLevel(navController)

    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addNotificationsTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.Notifications.route,
        startDestination = LeafScreen.Notifications.createRoute(Screen.Notifications)
    ){
        addNotificationsScreen(navController, Screen.Notifications)
        addGameDetails(navController, Screen.Notifications)
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addHomeScreenTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ){
        addHomeScreen(navController, Screen.Home)
        addGameDetails(navController, Screen.Home)
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addHomeScreen(
    navController: NavHostController,
    root : Screen
){
    composable(
        route = LeafScreen.Home.createRoute(root),
    ){
        NexusHomeRoute(vM = hiltViewModel(), navController,
            onOpenGameDetails = {
                    gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
            })
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addSettingsScreenTopLevel(
    navController: NavHostController,
){
    navigation(
        route = Screen.Settings.route,
        startDestination = LeafScreen.Settings.createRoute(Screen.Settings)
    ) {
        addSettingsScreen(navController, Screen.Settings)
        addChangeUsernameScreen(navController, Screen.Settings)
        addChangeProfilePictureScreen(navController, Screen.Settings)
        addChangeProfileBackgroundScreen(navController, Screen.Settings)
    }
}

private fun NavGraphBuilder.addChangeUsernameScreen(
    navController: NavHostController,
    root: Screen
){

    composable(
        route = LeafScreen.SettingsPages.createRoute(root,"username")
    ){
        NexusSettingsUsername(vM = hiltViewModel(), navController)
    }
}

private fun NavGraphBuilder.addChangeProfilePictureScreen(
    navController: NavHostController,
    root: Screen
){

    composable(
        route = LeafScreen.SettingsPages.createRoute(root,"picture")
    ){
        NexusSettingsPicture(vM = hiltViewModel(), navController)
    }
}


private fun NavGraphBuilder.addChangeProfileBackgroundScreen(
    navController: NavHostController,
    root: Screen
){

    composable(
        route = LeafScreen.SettingsPages.createRoute(root,"background")
    ){
        NexusSettingsBackground(vM = hiltViewModel(), navController)
    }
}


private fun NavGraphBuilder.addSettingsScreen(
    navController: NavHostController,
    root: Screen
){
    println(LeafScreen.Settings.createRoute(root))
    composable(
        route = LeafScreen.Settings.createRoute(root)

    ){
        NexusSettingsRoute(
            vM = hiltViewModel(),
            navController = navController,
            onUsernameClick = {navController.navigate(LeafScreen.SettingsPages.createRoute(root, "username"))},
            onPictureClick = {navController.navigate(LeafScreen.SettingsPages.createRoute(root, "picture"))},
            onBackgroundClick = {navController.navigate(LeafScreen.SettingsPages.createRoute(root, "background"))}
        )
    }

}
@ExperimentalComposeUiApi
private fun NavGraphBuilder.addSearchScreenTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.Search.route,
        startDestination = LeafScreen.Search.createRoute(Screen.Search)
    ) {
        addSearchScreen(navController, Screen.Search)
        addGameDetails(navController, Screen.Search)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
private fun NavGraphBuilder.addListScreenTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.List.route,
        startDestination = LeafScreen.List.createRoute(Screen.List)
    ) {
        addListScreen(navController, Screen.List)
        addGameDetails(navController, Screen.List)
    }
}


@ExperimentalComposeUiApi
private fun NavGraphBuilder.addSearchScreen(
    navController: NavHostController,
    root : Screen
){
    composable(
        route = LeafScreen.Search.createRoute(root)
    ){
    NexusSearchRoute(vM = hiltViewModel(), navController,
        onOpenGameDetails = {
                gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
        })
    }
}

private fun NavGraphBuilder.addGameDetails(
    navController: NavHostController,
    root: Screen
){
    composable(
        route = LeafScreen.GameDetail.createRoute(root),
        arguments = listOf(
            navArgument("gameId"){type = NavType.LongType}
        )
    ){
        NexusGameDetailRoute(vM = hiltViewModel(), navController, onOpenGameDetails = {
                gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
        })
    }
}

private fun NavGraphBuilder.addLoginScreenTopLevel(
    navController: NavHostController,
) {
    navigation(
        route = Screen.Login.route,
        startDestination = LeafScreen.Login.createRoute(Screen.Login)
    ) {
        addLoginScreen(navController, Screen.Login)
        addRegisterScreen(navController, Screen.Login)
    }
}

private fun NavGraphBuilder.addLoginScreen(
    navController: NavHostController,
    root: Screen,
){
    composable(
        route = LeafScreen.Login.createRoute(root),
    ){
        NexusLoginRoute(vM = hiltViewModel(), navController)
    }
}

private fun NavGraphBuilder.addRegisterScreen(
    navController: NavHostController,
    root: Screen,
) {
    composable(
        route = LeafScreen.Register.createRoute(root),
    ) {
        NexusRegisterRoute(vM = hiltViewModel(), navController)
    }
}

private fun NavGraphBuilder.addNotificationsScreen(
    navController: NavHostController,
    root: Screen
){
    composable(
        route = LeafScreen.Notifications.createRoute(root),
    ){
        NexusNotificationsRoute(vM = hiltViewModel(), navController, onOpenGameDetails = {
                gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
        })
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addListScreen(
    navController: NavHostController,
    root : Screen
){
    composable(
        route = LeafScreen.List.createRoute(root)
    ){
        NexusListRoute(vM = hiltViewModel(), navController,
            onOpenGameDetails = {
                    gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
            })
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addFriendListScreen(
    navController: NavHostController,
    root : Screen
) {

    composable(
        route = LeafScreen.ListScreen.createRoute(root),
        arguments = listOf(
            navArgument("userId"){type = NavType.StringType}
        )
    ){
        NexusFriendListRoute(vM = hiltViewModel(), navController,
            onOpenGameDetails = {
                    gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
            })
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
private fun NavGraphBuilder.addFriendsScreenTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.Friends.route,
        startDestination = LeafScreen.Friends.createRoute(Screen.Friends)
    ) {
        addFriendsScreen(navController, Screen.Friends)
        addFriendProfileScreen(navController, Screen.Friends)
        addFriendListScreen(navController, Screen.Friends)
        addGameDetails(navController, Screen.Friends)
    }
}
@ExperimentalComposeUiApi
private fun NavGraphBuilder.addFriendProfileScreen(
    navController: NavHostController,
    root: Screen
) {
    composable(
        route = LeafScreen.FriendScreen.createRoute(root),
        arguments = listOf(
            navArgument("userId"){type = NavType.StringType}
        )
    ) {
        NexusFriendProfileRoute(vM = hiltViewModel(), navController = navController, onOpenGameDetails = {
                gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
            }, onOpenList = {userId ->  navController.navigate(LeafScreen.ListScreen.createRoute(root, userId))}
        )
    }
}

@ExperimentalComposeUiApi
private fun NavGraphBuilder.addFriendsScreen(
    navController: NavHostController,
    root: Screen
){

    composable(
        route = LeafScreen.Friends.createRoute(root),
    ){
        NexusFriendsRoute(vM = hiltViewModel(), navController, onFriendProfile = {
                userId -> navController.navigate(LeafScreen.FriendScreen.createRoute(root, userId))
        }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfileScreenTopLevel(
    navController: NavHostController
){
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile)
    ){
        addProfileScreen(navController, Screen.Profile)
        addGameDetails(navController, Screen.Profile)
        addListScreen(navController, Screen.Profile)
    }
}

private fun NavGraphBuilder.addProfileScreen(
    navController: NavHostController,
    root: Screen
){
    composable(
        route = LeafScreen.Profile.createRoute(root),
    ){
        NexusProfileRoute(vM = hiltViewModel(), navController, onOpenGameDetails = {
                gameId -> navController.navigate(LeafScreen.GameDetail.createRoute(root, gameId))
        }, onOpenList = {_ -> navController.navigate(Screen.List.route)})
    }
}

val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph
