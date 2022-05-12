package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nexus.data.repositories.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NexusFriendsViewModel @Inject constructor(private val repo: FriendsRepository) : ViewModel(){

    private val searchTerm = repo.searchTerm //current search term in balk
    private val searching = repo.searching.value //kunt ge gebruiken om een loading circelte te tonen

    //moet true zijn als een search gedaan is om te weten of er no results afgebeeld moet worden of niet
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }


    fun getFriends(): Flow<List<String>> {
        return repo.getFriends()
    }

    fun storeFriend(f: String) = repo.storeFriend(f)



    //search bar stuff
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

    fun setSearchTerm(term: String) = repo.setSearchTerm(term)
}