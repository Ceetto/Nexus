package com.example.nexus.viewmodels

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

    fun getReleaseNotifcations(): StateFlow<List<ReleaseNotification>> {
        return notificationRepo.getReleaseNotifcations().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getFriendRequests(): StateFlow<List<FriendRequest>> {
        return notificationRepo.getFriendRequests().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
}