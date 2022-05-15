package com.example.nexus.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.repositories.*
import com.example.nexus.data.repositories.gameData.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject

@HiltViewModel
class NexusHomeViewModel  @Inject constructor(
    private val repo: HomeRepository,
    private val searchRepo: SearchRepository,
    private val listRepo: ListRepository,
    private val profileRepo: ProfileRepository,
    private val notificationsRepo: NotificationsRepository,
    private val friendsRepo: FriendsRepository,
    private val firebaseUpdater : FirebaseUpdater
) : ViewModel(){
    //home
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
                repoFetchGames()
                setRecommendedGames()
                searchingFavourite.value = false
                isRefreshing.value = false
            }catch(e: Exception){
                isRefreshing.value = false
                fetchGames()
            }
        }
    }

    suspend fun repoFetchGames() = withContext(Dispatchers.Default){
        repo.getGames()
        repo.getRecommendeds()
    }

    fun setRecommendedGames(){
        val newGameList = mutableListOf<Game>()
        for(game in favouriteList.value){
            val additions = mutableListOf<Game>()
            for(franchise in game.franchisesList){
                for(recGame in franchise.gamesList){
                    if(!gotIds.value.contains(recGame.id))
                        additions += recGame
                }
            }
            additions.shuffle()
            newGameList += additions.take(3)
        }
        for(game in favouriteList.value){
            val additions = mutableListOf<Game>()
            for(recGame in game.similarGamesList){
                if(!gotIds.value.contains(recGame.id))
                    additions += recGame
            }
            additions.shuffle()
            newGameList += additions.take(3)
        }
        recommendedGames = newGameList

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

    fun updateUser(){
        listRepo.updateUser()
        profileRepo.updateUser()
        friendsRepo.updateUser()
        notificationsRepo.updateUser()
//        firebaseUpdater.updateFirebaseData()
    }


    //search
    private val gameList = searchRepo.gameList.value
    private val searchTerm = searchRepo.searchTerm
    private val searching = searchRepo.searching.value
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }
    private val isRefreshingSearch = mutableStateOf(false)
    private val toLoad = searchRepo.toLoad.value

    fun onSearchEvent(){
        viewModelScope.launch {
            try{
                setToLoad(10)
                isRefreshingSearch.value = true
                fetchGamesSearch()
                isRefreshingSearch.value = false
            } catch(e: Exception){

            }
        }
    }

    fun onLoadMoreEvent(){
        viewModelScope.launch {
            fetchGamesSearch()
        }
    }

    suspend fun fetchGamesSearch() = withContext(Dispatchers.Default){
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

    fun isRefreshingSearch(): Boolean{
        return isRefreshingSearch.value
    }

    fun setToLoad(v : Int) = searchRepo.setToLoad(v)

    fun loadMore() {
        searchRepo.loadMore()
        onLoadMoreEvent()
    }
}