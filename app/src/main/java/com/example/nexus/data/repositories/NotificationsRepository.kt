package com.example.nexus.data.repositories


import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.Notification
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.db.FirebaseNotificationDao
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
                    game ->
                ((game.releaseDate - (currentTimeMillis() / 1000)) <= 31556926) && !game.notificationGiven
            } }
    }

    fun storeNotification(n: Notification) = notificationDao.storeNotification(n)

    fun getNotifications() = notificationDao.getNotifications()

    fun countNewNotifications() = notificationDao.countNewNotifications()

    fun sendFriendRequest(f: Friend, user: User){
        notificationDao.sendFriendRequest(f, user)
    }

    fun updateUser(){
        notificationDao.updateUser()
    }

    fun removeNotification(n: Notification) = notificationDao.removeNotification(n)
}