package com.example.nexus.data.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.example.nexus.data.repositories.list.ListRepository
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class HomeRepository @Inject constructor(
    val listRepo: ListRepository
) {

    var popularList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingPopular: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    var bestList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingBest: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    var trendingList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingTrending: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    var upcomingList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingUpcoming: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    var favouriteList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingFavourite: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    var dbDoneFetching = listRepo.doneFetching
    var gotIds: Lazy<MutableState<List<Long>>> = lazy{ mutableStateOf(ArrayList())}


    fun getGames(){
        val apicalypsePopular = APICalypse().fields("cover.image_id,name").where("rating_count > 0")
            .sort("rating_count", Sort.DESCENDING)
            .limit(20)
        val apicalypseBest = APICalypse().fields("cover.image_id,name").where("rating_count > 50")
            .sort("rating", Sort.DESCENDING)
            .limit(20)
        val apiCalypseTrending = APICalypse().fields("cover.image_id,name").where("rating_count > 0 & " +
                "first_release_date > ${System.currentTimeMillis()/1000 - 7600000}")
            .sort("rating_count", Sort.DESCENDING)
            .limit(20)
        val apiCalypseUpcoming = APICalypse().fields("cover.image_id,name").where("follows > 0 & " +
                "first_release_date > ${System.currentTimeMillis()/1000}")
            .sort("follows", Sort.DESCENDING)
            .limit(20)

        try{
            trendingList.value.value = fetchGames(apiCalypseTrending)
            searchingTrending.value.value = false
            upcomingList.value.value = fetchGames(apiCalypseUpcoming)
            searchingUpcoming.value.value = false
            bestList.value.value = fetchGames(apicalypseBest)
            searchingBest.value.value = false
            popularList.value.value = fetchGames(apicalypsePopular)
            searchingPopular.value.value = false

        }catch(e: RequestException){
            print("NEXUS API FETCH ERROR: ")
            println(e.message)
            println(e.result)
            searchingPopular.value.value = false
            searchingBest.value.value = false
            searchingTrending.value.value = false
            searchingUpcoming.value.value = false
        }
    }

    suspend fun getRecommendeds(){
        while(!dbDoneFetching.value){
        }
        val favourites = listRepo.getTop10Favorites()
        var favIds = ""
        for(li in favourites){
            favIds += li.gameId.toString() + ","
        }
        favIds = favIds.dropLast(1)

        val allGames = listRepo.getAllGamesAsState()
        gotIds.value.value = emptyList()
        for(li in allGames){
            gotIds.value.value += li.gameId
        }

        val apiCalypseFavourites = APICalypse().fields("cover.image_id,name," +
                "similar_games.cover.image_id,similar_games.name," +
                "franchises.games.name, franchises.games.cover.image_id")
            .where("id = ($favIds)")
        try{
            if(favourites.isNotEmpty()){
                favouriteList.value.value = fetchGames(apiCalypseFavourites)
            } else {
                favouriteList.value.value = emptyList()
            }
        }catch (e: RequestException){
            print("NEXUS API FETCH ERROR: ")
            println(e.message)
            println(e.result)
            searchingFavourite.value.value = false
        }
    }

    //for testing
    private var fetchGames : (APICalypse) -> List<Game> = { a : APICalypse -> IGDBWrapper.games(a)}
    fun setFetchGames(f : (APICalypse) -> List<Game>){
        fetchGames = f
    }

}