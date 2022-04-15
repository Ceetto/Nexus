package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.MainRepository
import com.example.nexus.data.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusNotificationsViewModel @Inject constructor(private val repo: NotificationsRepository) : ViewModel(){
}