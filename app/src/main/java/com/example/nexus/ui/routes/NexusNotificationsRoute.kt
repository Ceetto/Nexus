package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.data.dataClasses.ReleaseNotification
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.PushNotification
import com.example.nexus.viewmodels.NexusNotificationsViewModel
import kotlinx.coroutines.flow.stateIn

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun NexusNotificationsRoute(
    vM: NexusNotificationsViewModel,
    navController: NavHostController
){
    val context = LocalContext.current
    val channelId = "Nexus"
    val notificationId = 0
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Column {
            ReleaseNotificationItem("Game release!", "A game you where interested in is about to release.")
            FriendNotificationItem("New friend request!", "User has sent you a new friend Request.")
        }
        vM.storeReleaseNotification(ReleaseNotification(1, 1, 1))
        println(vM.getReleaseNotifications().value)
//        for (notification in vM.getReleaseNotifications().value) {
//            NotificationItem("Game releasing soon!", "test")
//        }
//        PushNotification(
//            context = context,
//            channelId = channelId,
//            notificationId = notificationId,
//            textTitle = "Nexus",
//            textContent = "Test"
//        )
    }
}

@Composable
fun ReleaseNotificationItem(type: String, detail: String) {
    Row( // 1
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) { // 3
            Text(
                text = type,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )

            Text(
                text = detail,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun FriendNotificationItem(type: String, detail: String) {
    Row( // 1
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) { // 3
            Text(
                text = type,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )

            Text(
                text = detail,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { /*TODO*/ },
                    content = { Text(text = "Accept") }
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { /*TODO*/ },
                    content = { Text(text = "Decline") }
                )
            }
        }
    }
}