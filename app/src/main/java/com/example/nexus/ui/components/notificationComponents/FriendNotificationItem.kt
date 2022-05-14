package com.example.nexus.ui.components.notificationComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Notification

@Composable
fun FriendNotificationItem(
    notification: Notification,
    storeFriend: (id: String) -> Unit,
    storeYourself: (id: String) -> Unit,
    removeNotification: (n: Notification) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            if (notification.pfp == "") {
                Image(
                    painter = rememberAsyncImagePainter(R.mipmap.ic_launcher_round),
                    contentDescription = "profile_picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(notification.pfp),
                    contentDescription = "profile_picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(
                text = notification.username,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )
            Text(
                text = notification.username + " has sent you a friend request."
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = {
                    storeFriend(notification.userId)
                    storeYourself(notification.userId)
                    removeNotification(notification)
                                         },
                    content = { Text(text = "Accept") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xff2ECC71))
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { removeNotification(notification) },
                    content = { Text(text = "Decline") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xffED4245))
                )
            }
        }
    }
}