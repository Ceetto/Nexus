package com.example.nexus.ui.components.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.User

@Composable
fun FriendItem(
    friend : Friend,
    setUserId: (String) -> Unit,
    removeFriend : (Friend) -> Unit,
    onFriendProfile: (userId: String) -> Unit
) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                setUserId(friend.userId);
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
                .fillMaxHeight())

//        IconButton(onClick = { removeFriend(friend) }) {
//            Icon(Icons.Rounded.Close, "removeFriend", Modifier.size(25.dp))
//        }
    }
}

@Composable
fun SearchUserItem(
    friend: Friend,
    getUser : () -> User,
    sendFriendRequest : (Friend, User) -> Unit,
    setUserId: (String) -> Unit,
    onFriendProfile: (userId: String) -> Unit
){
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                setUserId(friend.userId);
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
                .fillMaxHeight())

//        var sent by remember { mutableStateOf(false) }
//        IconButton(onClick = {if (!sent) {sendFriendRequest(friend, getUser())}; sent = true}) {
//
//            if (sent) {
//                Icon(Icons.Rounded.Check, "added friend", Modifier.size(25.dp))
//            } else {
//                Icon(Icons.Rounded.Add, "add friend", Modifier.size(25.dp))
//            }
//        }
    }

}