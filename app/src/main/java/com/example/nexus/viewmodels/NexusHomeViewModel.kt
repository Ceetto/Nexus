package com.example.nexus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nexus.data.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject

@HiltViewModel
class NexusHomeViewModel  @Inject constructor(private val repo: HomeRepository) : ViewModel(){
    private val popularList = repo.popularList.value
    private val searchingPopular = repo.searchingPopular.value
    private val bestList = repo.bestList.value
    private val searchingBest = repo.searchingBest.value
    private val trendingList = repo.trendingList.value
    private val searchingTrending = repo.searchingTrending.value
    private val upcomingList = repo.upcomingList.value
    private val searchingUpcoming = repo.searchingUpcoming.value
    
    
    init{
        fetchGames()
    }

    fun fetchGames(){

        viewModelScope.launch {
            try{
                repo.getGames()
            }catch(e: Exception){

            }
        }
    }

    fun getBestGames(): List<Game> {
        return bestList.value
    }
    fun isSearchingBest(): Boolean{
        return searchingBest.value
    }

    fun getPopularGames(): List<Game> {
        return popularList.value
    }
    fun isSearchingPopular(): Boolean{
        return searchingPopular.value
    }

    fun getTrendingGames(): List<Game> {
        return trendingList.value
    }
    fun isSearchingTrending(): Boolean{
        return searchingTrending.value
    }

    fun getUpcomingGames(): List<Game> {
        return upcomingList.value
    }
    fun isSearchingUpcoming(): Boolean{
        return searchingUpcoming.value
    }
}