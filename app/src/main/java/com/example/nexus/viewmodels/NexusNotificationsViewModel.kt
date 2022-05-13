package com.example.nexus.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.data.dataClasses.ReleaseNotification
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
) : ViewModel(){
    val dailyReleaseGames = notificationRepo.filterGames().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getReleaseNotifications(): StateFlow<List<ReleaseNotification>> {
        return notificationRepo.getReleaseNotifications().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getFriendRequests(): StateFlow<List<FriendRequest>> {
        return notificationRepo.getFriendRequests().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun storeReleaseNotification(releaseNotification: ReleaseNotification) = notificationRepo.storeReleaseNotification(releaseNotification)

    fun storeFriendRequest(friendRequest: FriendRequest) = notificationRepo.storeFriendRequest(friendRequest)
}