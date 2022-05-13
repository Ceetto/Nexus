package com.example.nexus.data.repositories


import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.Notification
import com.example.nexus.data.dataClasses.ReleaseNotification
import com.example.nexus.data.dataClasses.SortOptions
import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.db.FirebaseNotificationDao
import com.example.nexus.data.db.notifications.FirebaseFriendRequestDao
import com.example.nexus.data.db.notifications.ReleaseNotificationDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.*
import java.lang.System.currentTimeMillis

@Singleton
class NotificationsRepository @Inject constructor(
    private val notificationDao: FirebaseNotificationDao,
    private val firebaseListDao: FirebaseListDao
) {
    fun filterGames(): Flow<List<ListEntry>> {
        return firebaseListDao.getPlanned().map {
            it.filter {
                    game -> (game.releaseDate - (currentTimeMillis() / 1000)) <= 31556926
            } }
    }

    fun storeNotification(n: Notification) = notificationDao.storeNotification(n)

    fun getNotifications() = notificationDao.getNotifications()

    fun areAllNotificationsRead() = notificationDao.areAllNotificationsRead()

    fun updateUser(){
        notificationDao.updateUser()
    }

    fun removeNotification(n: Notification) = notificationDao.removeNotification(n)
}