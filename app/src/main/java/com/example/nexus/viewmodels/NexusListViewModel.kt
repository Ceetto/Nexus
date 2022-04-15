package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.FriendsRepository
import com.example.nexus.data.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusListViewModel  @Inject constructor(private val repo: ListRepository) : ViewModel(){
}