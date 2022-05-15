package com.example.nexus.ui.routes.profiles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.profileStats.ProfileScreen
import com.example.nexus.viewmodels.profile.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    ProfileScreen(
        getUser = {vM.getUser()},
        onOpenGameDetails = onOpenGameDetails,
        navController = navController,
        getCategoryByName = {s: String -> vM.getCategoryByName(s)},
        getFavourites = {vM.getFavourites()}
    )
}
