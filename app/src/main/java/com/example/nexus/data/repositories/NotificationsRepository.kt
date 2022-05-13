package com.example.nexus.data.repositories


import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.data.dataClasses.Notification
import com.example.nexus.data.dataClasses.ReleaseNotification
import com.example.nexus.data.db.FirebaseNotificationDao
import com.example.nexus.data.db.notifications.FirebaseFriendRequestDao
import com.example.nexus.data.db.notifications.ReleaseNotificationDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.*

@Singleton
class NotificationsRepository @Inject constructor(
    private val notificationDao: FirebaseNotificationDao
) {

    fun storeNotification(n: Notification) = notificationDao.storeNotification(n)

    fun getNotifications() = notificationDao.getNotifications()

    fun areAllNotificationsRead() = notificationDao.areAllNotificationsRead()

    fun updateUser(){
        notificationDao.updateUser()
    }
}