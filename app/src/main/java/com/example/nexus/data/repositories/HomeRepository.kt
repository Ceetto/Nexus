package com.example.nexus.data.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import proto.Game
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
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

    var recommendedList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var searchingRecommended: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(true)}

    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    suspend fun getGames() = withContext(Dispatchers.Default){
        println("GETTING GAMES")
//        searching.value.value = true
        val apicalypsePopular = APICalypse().fields("cover.image_id,name").where("rating_count > 0")
            .sort("rating_count", Sort.DESCENDING)
            .limit(10)
        val apicalypseBest = APICalypse().fields("cover.image_id,name").where("rating_count > 50")
            .sort("rating", Sort.DESCENDING)
            .limit(10)
        val apiCalypseTrending = APICalypse().fields("cover.image_id,name").where("rating_count > 0 & " +
                "first_release_date > ${System.currentTimeMillis()/1000 - 7000000}")
            .sort("rating_count", Sort.DESCENDING)
            .limit(10)
        val apiCalypseUpcoming = APICalypse().fields("cover.image_id,name").where("follows > 0 & " +
                "first_release_date > ${System.currentTimeMillis()/1000}")
            .sort("follows", Sort.DESCENDING)
            .limit(10)

        try{
            println("STILL GETTING GAMES")
            trendingList.value.value = IGDBWrapper.games(apiCalypseTrending)
            searchingTrending.value.value = false
            upcomingList.value.value = IGDBWrapper.games(apiCalypseUpcoming)
            searchingUpcoming.value.value = false
            bestList.value.value = IGDBWrapper.games(apicalypseBest)
            searchingBest.value.value = false
            popularList.value.value = IGDBWrapper.games(apicalypsePopular)
            searchingPopular.value.value = false
            println("GOT GAMES")

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

}