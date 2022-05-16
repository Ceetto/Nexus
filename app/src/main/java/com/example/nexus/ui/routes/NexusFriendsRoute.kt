package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.ui.components.friends.FriendItem
import com.example.nexus.ui.components.friends.SearchUserItem
import com.example.nexus.viewmodels.NexusFriendsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

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
        Column(){
            val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
            SearchBarComponent(
                placeholder = "Add new friends",
                onSearch = {
                    vM.setSearched(true)
                    vM.onSearchEvent()
                    keyboardController?.hide()
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
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    //check of friendsData up to date is met friends list
                    if (vM.doneFetchingFriends()
                        && friends.size == friendsData.size
                        && vM.getFetchedFriends() == friends.size) {
                        friends.forEach { friend ->
                            for (f in friendsData) {
                                if (f.userId == friend) {
                                    FriendItem(
                                        friend = f,
                                        setUserId = { s: String -> vM.setUserid(s) },
                                        onFriendProfile = onFriendProfile
                                    )
                                }
                            }
                        }
                    }
                }
            } else {

                SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = vM.isRefreshing()), onRefresh = { vM.onSearchEvent() }) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
//                            .fillMaxHeight()
                    ) {
                        if(vM.isRefreshing()){
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .height(50.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {}
                        } else {
                            val matches by vM.getSearchResults().collectAsState()
                            if (vM.doneFetching()) {
                                if (matches.isEmpty()) {
                                    if (vM.hasSearched()) {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text("no results", fontSize = 20.sp)
                                        }
                                    }
                                } else {
                                    Column(
                                        Modifier
                                            .fillMaxHeight()
                                    ) {
                                        matches.forEach() { friend ->
                                            SearchUserItem(
                                                friend = friend,
                                                setUserId = { s: String -> vM.setUserid(s) },
                                                onFriendProfile = onFriendProfile
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


