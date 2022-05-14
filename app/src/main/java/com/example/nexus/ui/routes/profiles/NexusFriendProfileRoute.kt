package com.example.nexus.ui.routes.profiles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.profileStats.ProfileScreen
import com.example.nexus.viewmodels.profile.NexusFriendProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusFriendProfileRoute(
    vM: NexusFriendProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    LaunchedEffect(Unit){
        vM.onGetFriendEvent()
    }
    ProfileScreen(
        getUser = {vM.getUser()},
        onOpenGameDetails = onOpenGameDetails,
        navController = navController,
        getCategoryByName = {s: String -> vM.getCategoryByName(s)},
        getFavourites = {vM.getFavourites()}
    )
}