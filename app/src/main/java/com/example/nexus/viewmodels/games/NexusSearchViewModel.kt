package com.example.nexus.viewmodels.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.igdb.request.IGDBWrapper
import com.example.nexus.data.repositories.gameData.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
//    fun getGameById(id: Long) = repo.getGameById(id)
    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
    fun setSearchTerm(term: String) = repo.setSearchTerm(term)
}