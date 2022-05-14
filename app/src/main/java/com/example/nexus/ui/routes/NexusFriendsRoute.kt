package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.viewmodels.NexusFriendsViewModel

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NexusFriendsRoute(
    vM: NexusFriendsViewModel,
    navController: NavHostController,
    onFriendProfile: (userId: String) -> Unit
){
    val friendsData by vM.getFriendsData().collectAsState()
    val friends by vM.getFriends().collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, Color.Red)) {
            val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
            SearchBarComponent(
                placeholder = "Add New Friends",
                onSearch = {
                    vM.setSearched(true);
                    vM.searchEvent();
                    keyboardController?.hide();
                },
                getSearchTerm = vM.getSearchTerm(),
                setSearchTerm = { s -> vM.setSearchTerm(s) },
                setSearched = { b -> vM.setSearched(b) },
                onCancel = {
                    vM.setSearched(false)
                    vM.setSearchTerm("")
                    vM.emptyList()
                }
            )
            if (vM.getSearchTerm().isEmpty()) {
                Row(modifier = Modifier.padding(5.dp)) {
                    Text(text = "Friends: ${friends.size}")
                }
                LazyColumn(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .border(1.dp, Color.Blue)
                ) {
                    items(friends) { friend ->
                        for (f in friendsData) {
                            if (f.userId == friend) {
                                FriendItem(friend = f, vM, onFriendProfile)
                                break
                            }
                        }
                    }
                }
            } else {
                println("in this if statement")
                if (!vM.hasSearched()){
                    println("test")
                } else {
                    println("test2")
                    val doneFetching by vM.doneFetching()
                    if (doneFetching) {
                        println("done fetching")
                        val matches by vM.getSearchResults().collectAsState()
                        println(matches.size)
                        LazyColumn(
                            Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .border(1.dp, Color.Blue)
                        ) {
                            items(matches) { user ->
                                searchUserItem(user, vM, onFriendProfile)
                            }
                        }
                    } else {
                        println("not done fetching")

                        CircularProgressIndicator()
                    }
                }

            }
        }
    }
}

@Composable
fun FriendItem(friend : Friend, vM: NexusFriendsViewModel, onFriendProfile: (userId: String) -> Unit) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
                .border(1.dp, Color.Green)
                .clickable {
                    vM.setUserid(friend.userId);
                    onFriendProfile(friend.userId)
                           },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (friend.profilePicture != "") {
                Image(
                    painter = rememberAsyncImagePainter(friend.profilePicture),
                    contentDescription = "friend logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(R.mipmap.ic_launcher_round),
                    contentDescription = "friend logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            Text(text = friend.username, Modifier.padding(10.dp))

            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()) // height and background only for demonstration

            IconButton(onClick = { vM.removeFriend(friend) }) {
                Icon(Icons.Rounded.Close, "removeFriend", Modifier.size(25.dp))
            }
        }
}

@Composable
fun searchUserItem(
    user: Friend,
    vM: NexusFriendsViewModel,
    onFriendProfile: (userId: String) -> Unit
){
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, Color.Green).clickable{vM.setUserid(user.userId); onFriendProfile(user.userId)},
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (user.profilePicture != "") {
            Image(
                painter = rememberAsyncImagePainter(user.profilePicture),
                contentDescription = "friend logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(R.mipmap.ic_launcher_round),
                contentDescription = "friend logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }

        Text(text = user.username, Modifier.padding(10.dp))

        Spacer(
            Modifier
                .weight(1f)
                .fillMaxHeight()) // height and background only for demonstration

        IconButton(onClick = {vM.sendFriendRequest(user)}) {
            Icon(Icons.Rounded.Add, "add friend", Modifier.size(25.dp))
        }
    }

}
