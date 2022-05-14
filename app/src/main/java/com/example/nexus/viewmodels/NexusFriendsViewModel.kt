package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.repositories.FriendsRepository
import com.example.nexus.data.repositories.NotificationsRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NexusFriendsViewModel @Inject constructor(
    private val repo: FriendsRepository,
    private val notifRepo: NotificationsRepository,
    private val profileRepo: ProfileRepository
    ) : ViewModel(){

    private val searchTerm = repo.searchTerm //current search term in balk
    private val searching = repo.searching.value //kunt ge gebruiken om een loading circelte te tonen

    //moet true zijn als een search gedaan is om te weten of er no results afgebeeld moet worden of niet
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }


    fun getFriends(): StateFlow<List<String>> {
        return repo.getFriends()
    }

    fun sendFriendRequest(f: Friend, user: User){
        notifRepo.sendFriendRequest(f, user)
    }

    fun storeFriend(f: String) = repo.storeFriend(f)

    fun isSearching(): Boolean{
        return searching.value
    }

    fun setSearched(b: Boolean){
        searched.value.value = b
    }

    fun hasSearched(): Boolean {
        return searched.value.value
    }

    fun getSearchTerm(): String {
        return searchTerm.value
    }

    fun getFriendsData() : StateFlow<List<Friend>> {
        return repo.getFriendsData()
    }


    fun setSearchTerm(term: String) = repo.setSearchTerm(term)

    fun removeFriend(f: Friend) {
        repo.removeFriend(f)
    }

    fun getSearchResults() : StateFlow<List<Friend>> {
        return repo.getUserMatches()
    }

    fun searchEvent(){
        repo.eventTrigger()
    }

    fun doneFetching() : MutableState<Boolean> {
        return repo.doneFetching()
    }

    fun setUserid(id: String){
        profileRepo.setFriendId(id)
    }

    fun getUser():User{
        return profileRepo.getUser()
    }
    fun emptyList() = repo.emptyList()
}