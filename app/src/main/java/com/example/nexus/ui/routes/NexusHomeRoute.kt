 package com.example.nexus.ui.routes

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusHomeViewModel


 @ExperimentalComposeUiApi
@Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel,
    navController: NavHostController
) {
     Scaffold(
         topBar = { NexusTopBar(navController = navController, canPop = false) }
     ) {
         Text(text="home")
     }


}



//@HiltViewModel
//class tmpVm @Inject constructor() : ViewModel(){
//    var gameList : MutableState<List<Game>> = mutableStateOf(ArrayList())
//    var coverList : MutableState<List<Cover>> = mutableStateOf(ArrayList())
//    val searchTerm = mutableStateOf("")
//    init {
//        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
//    }
//
//    fun onSearchEvent(){
//        viewModelScope.launch {
//            try{
//                getGames()
//                getCovers()
//            } catch(e: Exception){
//
//            }
//        }
//    }
//
//    suspend fun getGames() = withContext(Dispatchers.IO){
//        val apicalypse = APICalypse().fields("*").limit(3).search(searchTerm.value)
//        try{
//            val games: List<Game> = IGDBWrapper.games(apicalypse)
//            gameList.value = games
//        } catch(e: RequestException) {
//            print("NEXUS API FETCH ERROR:")
//            println(e.result)
//        }
//    }
//
//    suspend fun getCovers() = withContext(Dispatchers.IO){
////        val covers : MutableList<Cover> = ArrayList()
//        var ids : String = "("
//            try {
//                for(game in gameList.value) {
////                    val apicalypse = APICalypse().fields("*").where("(id = ${game.cover.id});")
////                    val cover: Cover = IGDBWrapper.covers(apicalypse)[0]
//////                    println(cover)
////                    covers.add(cover)
//////                    println(covers)
//                    println(game.cover)
//                    ids += game.id.toString() + ","
//                }
//                ids = ids.dropLast(1)
//                ids += ")"
//                println(ids)
//                val apicalypse = APICalypse().fields("*").where("game =$ids;")
//                val covers : List<Cover> = IGDBWrapper.covers(apicalypse)
//                println(covers)
//                coverList.value = covers
//            } catch (e: RequestException) {
//                print("NEXUS API FETCH ERROR:")
//                println(e.result)
//            }
//
//    }
//
//    fun getCoverWithId(id : Long) : Cover? {
//        for (cover in coverList.value){
//            if(cover.id == id){
//                return cover
//            }
//        }
//        return null
//    }
//
//    fun setSearchTerm(term: String){
//        this.searchTerm.value = term
//    }
//}
//
//data class GameAndCover(
//    val game: Game,
//    val cover: Cover?
//)