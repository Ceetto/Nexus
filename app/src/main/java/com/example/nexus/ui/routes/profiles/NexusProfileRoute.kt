package com.example.nexus.ui.routes.profiles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.User
import com.example.nexus.ui.components.profileStats.ProfileScreen
import com.example.nexus.viewmodels.profile.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit,
    onOpenList : (userId: String) -> Unit
){
    ProfileScreen(
        canBeFriend = false,
        isFriend = {false},
        getUser = {vM.getUser()},
        onOpenGameDetails = onOpenGameDetails,
        navController = navController,
        getCategoryByName = {s: String -> vM.getCategoryByName(s)},
        getFavourites = {vM.getFavourites()},
        getCurrentUser = {vM.getUser()},
        addFriend = { _: Friend, _: User -> },
        removeFriend = {},
        getFriend = {Friend("", "", "", "")},
        getNewFriend = {Friend("", "", "", "")},
        onOpenList = onOpenList,
        getUserId = vM.getUserId(),
        getShowRemoveFriendPopup = {false},
        setShowRemoveFriendPopup = {},
        getShowAddFriendPopup = {false},
        setShowAddFriendPopup = {},
    )
}
