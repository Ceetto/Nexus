package com.example.nexus.data.repositories.gameData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository
@Inject constructor
    (
//    private val fetchGames : (APICalypse) -> List<Game>
)
{

    var gameList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList())}
    val searchTerm = mutableStateOf("")
    var searching: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false)}
    var toLoad = lazy{mutableStateOf(10)}
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    fun getGames(){
        if(searchTerm.value.isNotEmpty()){
            searching.value.value = true
            val apicalypse = APICalypse().fields("cover.image_id,name, rating, rating_count")
                .where("(name ~ *\"${searchTerm.value}\"* | franchise.name ~ *\"${searchTerm.value}\"* "
                        + "| alternative_names.name ~ *\"${searchTerm.value}\"* | parent_game.name ~ *\"${searchTerm.value}\"* | remakes.name ~ *\"${searchTerm.value}\"*)"
                ).limit(toLoad.value.value)
//            gameList.value.value = kotlin.collections.emptyList()
            try{
//                gameList.value.value = IGDBWrapper.games(apicalypse)
                gameList.value.value = fetchGames(apicalypse)
                searching.value.value = false
            } catch(e: RequestException) {
                print("NEXUS API FETCH ERROR: ")
                println(e.message)
                println(e.result)
                searching.value.value = false
            }
        }
    }

    fun emptyList(){
        gameList.value.value = kotlin.collections.emptyList()
    }

    fun setSearchTerm(term: String){
        this.searchTerm.value = term
    }

    fun setToLoad(v : Int){
        toLoad.value.value = v
    }

    fun loadMore(){
        toLoad.value.value += 10
    }

    //for testing
    private var fetchGames : (APICalypse) -> List<Game> = { a : APICalypse -> IGDBWrapper.games(a)}
    fun setFetchGames(f : (APICalypse) -> List<Game>){
        fetchGames = f
    }
}


abstract class GameFetcher {
    companion object{
        fun getInstance(): (APICalypse) -> List<Game> {
            return {a : APICalypse -> IGDBWrapper.games(a)}
        }
    }
}

@InstallIn(SingletonComponent::class)
@Module
class FetchGamesFunctionModule {

    @Singleton
    @Provides
    fun provideGameFetcher(): (APICalypse) -> List<Game>{
        return GameFetcher.getInstance()
    }
}
