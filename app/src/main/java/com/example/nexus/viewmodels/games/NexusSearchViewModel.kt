package com.example.nexus.viewmodels.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.igdb.request.IGDBWrapper
import com.example.nexus.data.repositories.gameData.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject
import javax.inject.Qualifier

@HiltViewModel
class NexusSearchViewModel @Inject constructor(private val repo: SearchRepository,
                                               @Dispatcher private val dispatcher: CoroutineDispatcher,
                                               ) : ViewModel()
{

    private val gameList = repo.gameList.value
    private val searchTerm = repo.searchTerm
    private val searching = repo.searching.value
    private var searched : Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false) }

    fun onSearchEvent(){
        viewModelScope.launch {
            try{
                fetchGames()
            } catch(e: Exception){

            }
        }
    }

    suspend fun fetchGames() = withContext(dispatcher){
        repo.getGames()
    }

    fun getSearchTerm(): String {
        return searchTerm.value
    }

    fun setSearchTerm(term: String) = repo.setSearchTerm(term)

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

    fun emptyList() = repo.emptyList()
}


@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Dispatcher
    @Provides
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Dispatcher