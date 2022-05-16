package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.repositories.FriendsRepository
import com.example.nexus.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NexusFriendsViewModel @Inject constructor(
    private val repo: FriendsRepository,
    private val profileRepo: ProfileRepository
    ) : ViewModel(){

    private val searchTerm = repo.searchTerm //current search term in balk
    private val searching = repo.searching.value //kunt ge gebruiken om een loading circelte te tonen

    //moet true zijn als een search gedaan is om te weten of er no results afgebeeld moet worden of niet
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }

    private val isRefreshing = mutableStateOf(false)

    fun onSearchEvent(){
        isRefreshing.value = true
        fetchFriends()
        isRefreshing.value = false
    }

    fun fetchFriends(){
        repo.eventTrigger()
    }

    fun getFriends(): StateFlow<List<String>> {
        return repo.getFriends()
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

    fun getSearchResults() : StateFlow<List<Friend>> {
        return repo.getUserMatches()
    }

    fun setUserid(id: String){
        profileRepo.setFriendId(id)
    }

    fun emptyList() = repo.emptyList()

    fun isRefreshing(): Boolean{
        return isRefreshing.value
    }
}