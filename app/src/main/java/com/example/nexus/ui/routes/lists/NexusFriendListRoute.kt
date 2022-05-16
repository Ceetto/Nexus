package com.example.nexus.ui.routes.lists

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.list.ListCategoryComponent
import com.example.nexus.ui.components.list.ListTopNavigationBar
import com.example.nexus.viewmodels.list.NexusFriendListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusFriendListRoute(
    vM: NexusFriendListViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit,
){
    LaunchedEffect(Unit){
        vM.onGetListEvent()
    }

    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Scaffold(topBar = {
            ListTopNavigationBar(vM.getSelectedCategory(), vM::onSelectedCategoryChanged)
        }){
            ListCategoryComponent(
                category = vM.getSelectedCategory(),
                getCategoryByName = { category -> vM.getCategoryByName(category) },
                toggleDescendingOrAscendingIcon = {vM.toggleDescendingOrAscendingIcon()},
                getDescendingOrAscendingIcon = {vM.getDescendingOrAscendingIcon()},
                getSelectedCategory = {vM.getSelectedCategory()},
                setSortOption = {s -> vM.setSortOption(s)},
                getSortOption = {vM.getSortOption()},
                onOpenGameDetails = onOpenGameDetails)
        }
    }

}