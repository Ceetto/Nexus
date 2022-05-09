package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NexusFriendsViewModel @Inject constructor(private val repo: FriendsRepository) : ViewModel(){

    fun getFriends(): Flow<List<String>> {
        return repo.getFriends()
    }

    fun storeFriend(f: String) = repo.storeFriend(f)
}