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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.notificationComponents.FriendNotificationItem
import com.example.nexus.ui.components.notificationComponents.NoNotifications
import com.example.nexus.ui.components.notificationComponents.ReleaseNotificationItem
import com.example.nexus.viewmodels.NexusNotificationsViewModel


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
    val releaseNotifications by vM.getReleaseNotifications().collectAsState()
    val friendNotifications by vM.getFriendRequests().collectAsState()

    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Column(modifier = Modifier.padding(start = 4.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Game Notifications:",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 22.sp,
            )
            if (releaseNotifications.isEmpty()) {
                NoNotifications()
            } else {
                for (notification in releaseNotifications) {
                    ReleaseNotificationItem("Game releasing soon!", "A game you where interested in is about to release.")
                }
            }
            Text(
                text = "Friend Requests:",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 22.sp,
            )
            if (releaseNotifications.isEmpty()) {
                NoNotifications()
                vM.storeFriendRequest(FriendRequest("test", 0))
            } else {
                for (FriendRequest in friendNotifications) {
                    FriendNotificationItem("New friend request!", "User has sent you a new friend Request." )
                }
            }
        }
//        PushNotification(
//            context = context,
//            channelId = channelId,
//            notificationId = notificationId,
//            textTitle = "Nexus",
//            textContent = "Test"
//        )
    }
}