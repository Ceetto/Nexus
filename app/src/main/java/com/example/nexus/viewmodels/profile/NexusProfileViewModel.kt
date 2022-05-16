package com.example.nexus.viewmodels.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.repositories.list.ListRepository
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusProfileViewModel @Inject constructor(private val profileRepo: ProfileRepository,
                                                private  val listRepo: ListRepository,
                                                private val loginRepo: LoginRepository) : ViewModel(){

    private val username = mutableStateOf("")

    fun getCategoryByName(category: String): StateFlow<List<ListEntry>> {
        return listRepo.getCategoryByName(category).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getFavourites(): StateFlow<List<ListEntry>> {
        return listRepo.favorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getUsername() = profileRepo.getUsername()

    fun getNewUsername() : String {
        return username.value
    }
    fun getUserId() : String{
        return profileRepo.getUserId()
    }

    fun setUsername(username : String){
        this.username.value = username
    }

    fun storeUsername(username : String){
        profileRepo.updateUsername(username)
    }

    fun storePicture(picture : Uri){
        profileRepo.updateProfilePicture(picture)
    }

    fun storeBackground(picture : Uri){
        profileRepo.updateBackground(picture)
    }

    fun getProfilePicture() : String {
        return profileRepo.getProfilePicture()
    }

    fun getBackground() : String {
        return profileRepo.getBackground()
    }

    fun getUser() : User {
        return profileRepo.getUser()
    }

    fun logOut() = loginRepo.signOut()

//    fun setUserid(id: String){
//        profileRepo.setFriendId(id)
//    }
}