package com.example.nexus.ui.components.profileStats

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.theme.NexusGray
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    getUser : () -> User,
    onOpenGameDetails: (gameId: Long) -> Unit,
    navController: NavHostController,
    getCategoryByName : (String) -> StateFlow<List<ListEntry>>,
    getFavourites: () -> StateFlow<List<ListEntry>>,
    canBeFriend: Boolean,
    isFriend: () -> Boolean,
    addFriend : (Friend, User) -> Unit,
    removeFriend : (Friend) -> Unit,
    getCurrentUser : () -> User,
    getFriend : () -> Friend,
    getNewFriend : () -> Friend,
    onOpenList: (userId: String) -> Unit,
    getUserId: String,
    getShowRemoveFriendPopup: () -> Boolean,
    setShowRemoveFriendPopup : (Boolean) -> Unit,
    getShowAddFriendPopup: () -> Boolean,
    setShowAddFriendPopup : (Boolean) -> Unit
){
    val background = getUser().profileBackground
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

            ) {
                if (background != "") {
                    Image(
                        painter = rememberAsyncImagePainter(background),
                        contentDescription = "profile-background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RectangleShape)
                            .background(Color.LightGray)
                    )
                }
            }
            if(canBeFriend){
                var removed by remember { mutableStateOf(false)}
                var added by remember { mutableStateOf(false)}
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp), horizontalAlignment = Alignment.End) {
                    if (isFriend()) {
                        IconButton(onClick = {
                            setShowRemoveFriendPopup(true)
                        }) {
                            Box(
                                modifier = Modifier
                                    .size(65.dp)
                                    .clip(CircleShape)
                                    .background(Color.LightGray)
                            ){
                                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    if(!removed){
                                        Icon(
                                            imageVector = Icons.Default.PersonRemove,
                                            contentDescription = "remove friend",
                                            tint = NexusGray,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Text("remove friend", color = NexusGray, fontSize = 9.sp)
                                    }
                                }
                            }
                        }
                    } else {
                        if(!added){
                            IconButton(onClick = { setShowAddFriendPopup(true) }) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                ) {
                                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.PersonAdd,
                                            contentDescription = "add friend",
                                            tint = NexusGray,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Text("add friend", color = NexusGray, fontSize = 9.sp)
                                    }
                                }
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "add friend",
                                tint = NexusGray,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }


                if (getShowRemoveFriendPopup()){
                    AlertDialog(
                        onDismissRequest = { setShowRemoveFriendPopup(false)},
                        confirmButton = {
                            TextButton(onClick = {
                                setShowRemoveFriendPopup(false)
                                val f : Friend = getFriend()
                                if(f.username.isNotEmpty())
                                    removeFriend(getFriend())
                                removed = true
                            })
                            { Text(text = "Yes", color=MaterialTheme.colors.onBackground) }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                setShowRemoveFriendPopup(false) }) {
                                Text(text = "Cancel", color=MaterialTheme.colors.onBackground)
                            }},
                        title = { Text(text = "Remove friend") },
                        text = { Text(text = "Are you sure you want to remove this friend?") }
                    )
                }

                if (getShowAddFriendPopup()){
                    AlertDialog(
                        onDismissRequest = { setShowAddFriendPopup(false)},
                        confirmButton = {
                            TextButton(onClick = {
                                setShowAddFriendPopup(false)
                                addFriend(getNewFriend(), getCurrentUser())
                                added = true
                            })
                            { Text(text = "Yes", color=MaterialTheme.colors.onBackground) }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                setShowAddFriendPopup(false) }) {
                                Text(text = "Cancel", color=MaterialTheme.colors.onBackground)
                            }},
                        title = { Text(text = "Send friend request") },
                        text = { Text(text = "Send a friend request to this user?") }
                    )
                }
            }



            ProfilePicture(onOpenGameDetails, getUser, getCategoryByName, getFavourites, onOpenList, getUserId, canBeFriend)

        }
    }

}

@Composable
fun ProfilePicture(
    onOpenGameDetails: (gameId: Long) -> Unit,
    getUser : () -> User,
    getCategoryByName : (String) -> StateFlow<List<ListEntry>>,
    getFavourites: () -> StateFlow<List<ListEntry>>,
    onOpenList: (userId: String) -> Unit,
    getUserId: String,
    canBeFriend: Boolean
){
    val profilePic = getUser().profilePicture
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (profilePic != ""){
            Image(
                painter = rememberAsyncImagePainter(profilePic),
                contentDescription = "profile_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(R.mipmap.ic_launcher_round),
                contentDescription = "profile_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

        }

        Text(text = getUser().username, modifier = Modifier.padding(10.dp))

        ProfileStats(getCategoryByName)
        if(canBeFriend){
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {onOpenList(getUserId);}){
                    Text(text = "View List")
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
            horizontalAlignment = Alignment.Start) {
            FavoriteList(getFavourites, onOpenGameDetails)
        }
    }
}

@Composable
fun FavoriteList(
    getFavourites: () -> StateFlow<List<ListEntry>>,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    val favorites by getFavourites().collectAsState()
    Column(
        Modifier.padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
    ){
        Text("Favourites", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Row(Modifier.horizontalScroll(rememberScrollState())){
            favorites.forEach { entry -> Row{
                    FavoriteListComponent(entry, onOpenGameDetails, LocalFocusManager.current)
                }
            }
        }
        if(favorites.isEmpty()){
            Text("no favourites", fontSize = 12.sp)
        }
    }
}

@Composable
fun FavoriteListComponent(entry: ListEntry, onOpenGameDetails: (gameId: Long) -> Unit, focusManager: FocusManager) {
    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                onOpenGameDetails(entry.gameId)
                focusManager.clearFocus()
            })
        }
        .width(127.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(entry.coverUrl),
            contentDescription = "cover image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(127.dp, 150.dp)
                .padding(end = 5.dp)
        )
        Text(entry.title)
    }
}

