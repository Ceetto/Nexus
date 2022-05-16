package com.example.nexus.ui.routes.profiles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.User
import com.example.nexus.ui.components.profileStats.ProfileScreen
import com.example.nexus.viewmodels.profile.NexusFriendProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusFriendProfileRoute(
    vM: NexusFriendProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    ProfileScreen(
        canBeFriend = true,
        isFriend = {vM.isFriend()},
        getUser = {vM.getUser()},
        onOpenGameDetails = onOpenGameDetails,
        navController = navController,
        getCategoryByName = {s: String -> vM.getCategoryByName(s)},
        getFavourites = {vM.getFavourites()},
        getCurrentUser = {vM.getCurrentUser()},
        addFriend = { f: Friend, u: User -> vM.sendFriendRequest(f, u)},
        removeFriend = {f: Friend -> vM.removeFriend(f)},
        getFriend = {vM.getFriend()},
        getNewFriend = {vM.getNewFriend()}
    )
}