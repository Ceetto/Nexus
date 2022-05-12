package com.example.nexus.data.repositories


import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.data.dataClasses.ReleaseNotification
import com.example.nexus.data.db.notifications.FirebaseFriendRequestDao
import com.example.nexus.data.db.notifications.ReleaseNotificationDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.*

@Singleton
class NotificationsRepository @Inject constructor(
    private val friendRequestDao: FirebaseFriendRequestDao,
    private val notificationDao: ReleaseNotificationDao
) {
    fun getReleaseNotifications(): Flow<List<ReleaseNotification>>{
        return notificationDao.getReleaseNotifications()
    }

    fun getFriendRequests(): Flow<List<FriendRequest>>{
        return friendRequestDao.getFriendRequests()
    }

    fun storeReleaseNotification(n: ReleaseNotification) = notificationDao.storeReleaseNotification(n)

    fun storeFriendRequest(r: FriendRequest) = friendRequestDao.storeFriendRequest(r)

    fun updateUser(){
        friendRequestDao.updateUser()
        notificationDao.updateUser()
    }
}