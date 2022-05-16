package com.example.nexus

import com.example.nexus.data.repositories.HomeRepository
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import com.example.nexus.data.repositories.gameData.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import proto.Game


@ExperimentalCoroutinesApi
class ApiTests {
    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchGamesBySearchTermShouldExecuteAPIFunctionToFindGames() = runTest()
    {

        fun fetchGames(): List<Game>{
            return arrayListOf(Game.getDefaultInstance(), Game.getDefaultInstance(), Game.getDefaultInstance())
        }

        val searchRepo = SearchRepository()
        searchRepo.setSearchTerm("potato")
        searchRepo.setFetchGames { fetchGames() }
        withContext(Dispatchers.Default){
            searchRepo.getGames()
        }
        advanceUntilIdle()
        Assert.assertTrue(searchRepo.gameList.value.value.isNotEmpty())
    }

    @Test
    fun searchGameByIdShouldExecuteAPICallFunctionToFindGameWithThatId() = runTest(){
        fun fetchGames(): List<Game>{
            return arrayListOf(Game.getDefaultInstance())
        }

        val gameRepo = GameDetailRepository()
        gameRepo.setFetchGames { fetchGames() }
        withContext(Dispatchers.Default){
            gameRepo.getGameById(Game.getDefaultInstance().id)
        }
        advanceUntilIdle()
        Assert.assertTrue(gameRepo.gameList.value.value.isNotEmpty())
    }

    @Test
    fun searchGamesForHomePageShouldExecuteAll() = runTest()
    {

        fun fetchGames(): List<Game>{
            return arrayListOf(Game.getDefaultInstance(), Game.getDefaultInstance(), Game.getDefaultInstance())
        }

        val homeRepo = HomeRepository(mock())
        homeRepo.setFetchGames { fetchGames() }
        withContext(Dispatchers.Default){
            homeRepo.getGames()
        }
        advanceUntilIdle()
        Assert.assertTrue(homeRepo.popularList.value.value.isNotEmpty())
    }

}