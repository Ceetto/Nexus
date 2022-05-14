package com.example.nexus.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.*
import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.repositories.FriendsRepository
import com.example.nexus.data.repositories.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusNotificationsViewModel @Inject constructor(
    private val notificationRepo: NotificationsRepository,
    private val firebaseListDao: FirebaseListDao,
    private val friendsRepo: FriendsRepository
) : ViewModel(){
    private val releaseGames = notificationRepo.filterGames().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getReleaseGames(): StateFlow<List<ListEntry>> {
        return releaseGames
    }

    fun getNotifications() = notificationRepo.getNotifications().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun storeNewNotifications(entries: List<ListEntry>) {
        for (entry in entries) {
            notificationRepo.storeNotification(Notification("", entry.gameId, entry.releaseDate,
                System.currentTimeMillis(), false, "", entry.coverUrl, "", entry.title,
                NotificationType.RELEASE_DATE.value
            ))
            firebaseListDao.storeListEntry(
                ListEntry(entry.gameId, entry.title, entry.score, entry.minutesPlayed, entry.status,
                    entry.coverUrl, entry.favorited, entry.releaseDate, true))
        }
    }

    fun storeFriend(id: String) = friendsRepo.storeFriend(id)

    fun storeYourself(id: String) = friendsRepo.storeYourself(id)

    fun storeNewNotification(n: Notification) = notificationRepo.storeNotification(n)

    fun removeNotification(n: Notification) = notificationRepo.removeNotification(n)

    fun readNotifications(entries: List<Notification>) {
        for (entry in entries) {
            notificationRepo.storeNotification(
                Notification(entry.userId, entry.gameId, entry.releaseDate, entry.notificationTime,
                    true, entry.pfp, entry.gameCover, entry.username, entry.gameName, entry.notificationType
                ))
        }
    }

    fun countNewNotifications() = notificationRepo.countNewNotifications()
}