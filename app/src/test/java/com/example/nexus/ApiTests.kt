package com.example.nexus

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nexus.data.repositories.gameData.SearchRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*


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

    @Test
    fun searchGamesBySearchTermShouldFindGames() = runTest()
//    = withContext(Dispatchers.Default)
    {
        withContext(Dispatchers.Default){
            val searchRepo = SearchRepository()
            searchRepo.setSearchTerm("pokemon")
            searchRepo.getGames()
            Assert.assertTrue(searchRepo.gameList.value.value.isNotEmpty())
        }

    }

}