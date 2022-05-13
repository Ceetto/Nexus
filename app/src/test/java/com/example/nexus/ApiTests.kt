package com.example.nexus

import com.example.nexus.data.repositories.gameData.SearchRepository
import com.example.nexus.viewmodels.games.NexusSearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ApiTests {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

//    @Test
//    fun searchGamesByName() = runBlockingTest{
//        val vM = NexusSearchViewModel(SearchRepository(), testDispatcher)
//        vM.setSearchTerm("kiseki")
////        launch{
////            vM.fetchGames()
////        }
////        vM.fetchGames()
//        vM.onSearchEvent()
//        assert(vM.getGameList().isNotEmpty())
//    }

}