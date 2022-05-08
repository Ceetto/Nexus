package com.example.nexus.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.data.repositories.LoginRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusProfileViewModel @Inject constructor(private val profileRepo: ProfileRepository,
                                                private  val listRepo: ListRepository,
                                                private val loginRepo: LoginRepository) : ViewModel(){

    private val username = mutableStateOf("")
    val favorites = listRepo.favorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getCategoryByName(category: String): StateFlow<List<ListEntry>> {
        return listRepo.getCategoryByName(category).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getUsername() = profileRepo.getUsername()

    fun getNewUsername() : String {
        return username.value
    }

    fun setUsername(username : String){
        this.username.value = username
    }

    fun storeUsername(username : String){
        profileRepo.updateUsername(username)
    }

    fun logOut() = loginRepo.signOut()


}