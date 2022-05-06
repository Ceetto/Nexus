package com.example.nexus.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val favouriteList = repo.favouriteList.value
    private val searchingFavourite = repo.searchingFavourite.value
    private val isRefreshing = mutableStateOf(false)

    private var recommendedGames = mutableListOf<Game>()

    private val gotIds = repo.gotIds.value
    
    init{
        fetchGames()
    }

    fun fetchGames(){

        viewModelScope.launch {
            try{
                isRefreshing.value = true
                searchingBest.value = true
                searchingPopular.value = true
                searchingTrending.value = true
                searchingUpcoming.value = true
                searchingFavourite.value = true
                repo.getGames()
                setRecommendedGames()
                searchingFavourite.value = false
                isRefreshing.value = false
            }catch(e: Exception){
                isRefreshing.value = false
                fetchGames()
            }
        }
    }

    fun setRecommendedGames(){
        for(game in favouriteList.value){
            val additions = mutableListOf<Game>()
            for(franchise in game.franchisesList){
                for(recGame in franchise.gamesList){
                    if(!gotIds.value.contains(recGame.id))
                        additions += recGame
                }
            }
            additions.shuffle()
            recommendedGames += additions.take(3)
        }
        for(game in favouriteList.value){
            val additions = mutableListOf<Game>()
            for(recGame in game.similarGamesList){
                if(!gotIds.value.contains(recGame.id))
                    additions += recGame
            }
            additions.shuffle()
            recommendedGames += additions.take(3)
        }

        if(recommendedGames.size > 20){
            recommendedGames.shuffle()
            recommendedGames = recommendedGames.take(20).toMutableList()
        }
    }

    fun getRecommendedGames(): List<Game>{
        return recommendedGames
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

    fun getFavouriteGames(): List<Game> {
        return favouriteList.value
    }
    fun isSearchingFavourite(): Boolean{
        return searchingFavourite.value
    }

    fun isRefreshing(): Boolean{
        return isRefreshing.value
    }

    fun getGotIds(): List<Long> {
        return gotIds.value
    }
}