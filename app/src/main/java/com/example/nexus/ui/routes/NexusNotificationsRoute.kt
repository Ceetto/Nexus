package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.data.dataClasses.NotificationType
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.notificationComponents.FriendNotificationItem
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
    //val releaseNotifications by vM.getReleaseNotifications().collectAsState()
    //val friendNotifications by vM.getFriendRequests().collectAsState()
    val newNotifications by vM.getReleaseGames().collectAsState()
    val notifications by vM.getNotifications().collectAsState()
    vM.storeNewNotifications(newNotifications)
    vM.readNotifications(notifications)

    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Column(modifier = Modifier
            .padding(start = 4.dp)
            .verticalScroll(rememberScrollState())
        ) {
            val notifications by vM.getNotifications().collectAsState()
            for (notification in notifications) {
                if (notification.notificationType == NotificationType.RELEASE_DATE.value) {
                    ReleaseNotificationItem(
                        notification = notification,
                        removeNotification = { e -> vM.removeNotification(e) }
                    )
                } else {
                    FriendNotificationItem(notification)
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