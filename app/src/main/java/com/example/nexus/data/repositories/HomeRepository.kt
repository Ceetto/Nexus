package com.example.nexus.data.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor() {

    var popularList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    var bestList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList()) }
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    fun getGames(){
        val apicalypsePopular = APICalypse().fields("cover.*,*").where("rating_count > 0")
            .sort("rating_count", Sort.DESCENDING).limit(20)
        val apicalypseBest = APICalypse().fields("cover.*,*").where("rating_count > 50")
            .sort("rating", Sort.DESCENDING).limit(20)
        try{
            popularList.value.value = IGDBWrapper.games(apicalypsePopular)
            bestList.value.value = IGDBWrapper.games(apicalypseBest)
        }catch(e: RequestException){
            print("NEXUS API FETCH ERROR: ")
            println(e.message)
            println(e.result)
        }
    }

}