package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.covers
import com.api.igdb.request.games
import com.example.nexus.data.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Cover
import proto.Game
import javax.inject.Inject

@HiltViewModel
class NexusSearchViewModel  @Inject constructor(private val repo: SearchRepository) : ViewModel(){
    var gameList = repo.gameList
    var coverList = repo.coverList
    val searchTerm = repo.searchTerm
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "f46j0jimr573o7re4efaqfnv5kbi7x")
    }
    fun onSearchEvent(){
        viewModelScope.launch {
            try{
                repo.getGames()
                repo.getCovers()
            } catch(e: Exception){

            }
        }
    }
    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
    fun setSearchTerm(term: String) = repo.setSearchTerm(term)
}