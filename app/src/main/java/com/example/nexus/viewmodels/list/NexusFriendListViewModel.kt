package com.example.nexus.viewmodels.list

import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NexusFriendListViewModel @Inject constructor(private val repo: ListRepository) : ViewModel(){

}