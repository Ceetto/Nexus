package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusBottomBarViewModel @Inject constructor(private val repo: NotificationsRepository) : ViewModel(){
    fun countNewNotifications() = repo.countNewNotifications()
}