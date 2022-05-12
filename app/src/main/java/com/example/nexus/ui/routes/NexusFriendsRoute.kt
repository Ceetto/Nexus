package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.viewmodels.NexusFriendsViewModel

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusFriendsRoute(
    vM: NexusFriendsViewModel,
    navController: NavHostController
){
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Column() {
            SearchBarComponent(
                placeholder = "search friends lmao",
                onSearch = { /*TODO*/ },
                getSearchTerm = vM.getSearchTerm(),
                setSearchTerm = {s -> vM.setSearchTerm(s)},
                setSearched = {b-> vM.setSearched(b)},
                onCancel = { /*TODO*/}
            )
        }
    }

}