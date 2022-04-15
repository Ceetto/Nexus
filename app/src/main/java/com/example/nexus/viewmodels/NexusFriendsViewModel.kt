package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.FriendsRepository
import com.example.nexus.data.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusFriendsViewModel @Inject constructor(private val repo: FriendsRepository) : ViewModel(){
}