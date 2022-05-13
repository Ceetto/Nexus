package com.example.nexus.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.*
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
    private val releaseGames = notificationRepo.filterGames().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getReleaseGames(): StateFlow<List<ListEntry>> {
        return releaseGames
    }

    fun getNotifications() = notificationRepo.getNotifications().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun storeNewNotifications(entries: List<ListEntry>) {
        for (entry in entries) {
            notificationRepo.storeNotification(Notification("", entry.gameId, entry.releaseDate,
                System.currentTimeMillis(), false, NotificationType.RELEASE_DATE.value
            ))
        }
    }
}