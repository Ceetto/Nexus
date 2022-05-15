package com.example.nexus.viewmodels.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusFriendProfileViewModel @Inject constructor(private val profileRepo: ProfileRepository,
                                                      private  val listRepo: ListRepository,
                                                      private val loginRepo: LoginRepository,
                                                      savedStateHandle: SavedStateHandle,
) : ViewModel(){

    private val friendId: String = savedStateHandle["userId"]!!
    private val favorites = listRepo.favorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onGetFriendEvent(){
        setFriend()
    }

    fun getUser() : User {
        return profileRepo.getFriend()
    }

    fun setFriend(){
        profileRepo.setFriendId(friendId)
    }

    fun getUserId():String{
        return friendId
    }
    fun getCategoryByName(category: String): StateFlow<List<ListEntry>> {
        return listRepo.getCategoryByName(category).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getFavourites(): StateFlow<List<ListEntry>> {
        return favorites
    }


}