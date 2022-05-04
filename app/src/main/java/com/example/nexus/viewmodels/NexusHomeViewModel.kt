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
    private val bestList = repo.bestList.value

    init{
        viewModelScope.launch {
            try{
                fetchGames()
            }catch(e: Exception){

            }
        }
    }

    suspend fun fetchGames() = withContext(Dispatchers.Default){
        repo.getGames()
    }

    fun getBestGames(): List<Game> {
        return bestList.value
    }
}