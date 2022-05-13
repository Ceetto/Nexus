package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.getUserId
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.viewmodels.NexusFriendsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NexusFriendsRoute(
    vM: NexusFriendsViewModel,
    navController: NavHostController
){
    val friendsData by vM.getFriendsData().collectAsState()
    val friends by vM.getFriends().collectAsState()
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

            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = "Friends: ${friends.size}")
            }

            vM.storeFriend("8qRFWZXfzlRxrbvrEikJxOEHdNr2")
            LazyColumn(
            ){
                items(friends) {friend ->
                    for (f in friendsData){
                        if (f.userId == friend){
                            FriendItem(friend = f)
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FriendItem(friend : Friend) {

    Row(modifier = Modifier
        .height(30.dp)
        .fillMaxWidth()) {

        Image(
            painter = rememberAsyncImagePainter(friend.profilePicture),
            contentDescription = "friend logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(25.dp)
        )

        Text(text = "Username = ${friend.username}")
    }
}
