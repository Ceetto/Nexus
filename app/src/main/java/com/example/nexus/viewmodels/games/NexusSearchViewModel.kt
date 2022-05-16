package com.example.nexus.viewmodels.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.repositories.gameData.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject

@HiltViewModel
class NexusSearchViewModel @Inject constructor(private val searchRepo: SearchRepository,
) : ViewModel() {
    private val gameList = searchRepo.gameList.value
    private val searchTerm = searchRepo.searchTerm
    private val searching = searchRepo.searching.value
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }
    private val isRefreshing = mutableStateOf(false)

    fun onSearchEvent(){
        viewModelScope.launch {
            try{
                setToLoad(10)
                isRefreshing.value = true
                fetchGames()
                isRefreshing.value = false
            } catch(e: Exception){

            }
        }
    }

    fun onLoadMoreEvent(){
        viewModelScope.launch {
            fetchGames()
        }
    }

    suspend fun fetchGames() = withContext(Dispatchers.Default){
        searchRepo.getGames()
    }

    fun getSearchTerm(): String {
        return searchTerm.value
    }

    fun setSearchTerm(term: String) = searchRepo.setSearchTerm(term)

    fun getGameList(): List<Game> {
        return gameList.value
    }

    fun isSearching(): Boolean{
        return searching.value
    }

    fun setSearched(b: Boolean){
        searched.value.value = b
    }

    fun hasSearched(): Boolean {
        return searched.value.value
    }

    fun emptyList() = searchRepo.emptyList()

    fun isRefreshing(): Boolean{
        return isRefreshing.value
    }

    fun setToLoad(v : Int) = searchRepo.setToLoad(v)

    fun loadMore() {
        searchRepo.loadMore()
        onLoadMoreEvent()
    }
}
