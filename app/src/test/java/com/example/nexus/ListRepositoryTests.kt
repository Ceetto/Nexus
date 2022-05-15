package com.example.nexus
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.SortOptions
import com.example.nexus.data.db.list.FirebaseListDao
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.ui.routes.ListCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.kotlin.mock

class ListRepositoryTests {

    lateinit var games : MutableStateFlow<List<ListEntry>>
    lateinit var dao: FirebaseListDao
    lateinit var repo: ListRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        games = MutableStateFlow(listOf(
            ListEntry(0, "b", 1, 666, ListCategory.DROPPED.value, "", false, 123),
            ListEntry(0, "c", 10, 42, ListCategory.PLANNED.value, "", false, 312),
            ListEntry(0, "a", 4, 69, ListCategory.PLAYING.value, "", false, 777),))
        dao = mock()
        repo = ListRepository(dao)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun setDescendingShouldSetDescending(){
        repo.setDescending(false)
        assertFalse(repo.isDescending())
        repo.setDescending(true)
        assertTrue(repo.isDescending())
    }

    @Test
    fun setSortOptionsShouldSetSortOption(){
        repo.setSortOption(SortOptions.STATUS.value)
        assertEquals(repo.getSortOption(), SortOptions.STATUS.value)

        repo.setSortOption(SortOptions.ALPHABETICALLY.value)
        assertEquals(repo.getSortOption(), SortOptions.ALPHABETICALLY.value)

        repo.setSortOption(SortOptions.SCORE.value)
        assertEquals(repo.getSortOption(), SortOptions.SCORE.value)

        repo.setSortOption(SortOptions.TIME_PLAYED.value)
        assertEquals(repo.getSortOption(), SortOptions.TIME_PLAYED.value)

        repo.setSortOption(SortOptions.RELEASE_DATE.value)
        assertEquals(repo.getSortOption(), SortOptions.RELEASE_DATE.value)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAlphabeticalSortAscending() = runTest {
        repo.setSortOption(SortOptions.ALPHABETICALLY.value)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        repo.setDescending(false)
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].title, "a")
        assertEquals(sortedGames.value[1].title, "b")
        assertEquals(sortedGames.value[2].title, "c")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testAlphabeticalSortDescending() = runTest {
        repo.setSortOption(SortOptions.ALPHABETICALLY.value)
        repo.setDescending(true)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].title, "c")
        assertEquals(sortedGames.value[1].title, "b")
        assertEquals(sortedGames.value[2].title, "a")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testScoreSortAscending() = runTest {
        repo.setSortOption(SortOptions.SCORE.value)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        repo.setDescending(false)
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].score, 1)
        assertEquals(sortedGames.value[1].score, 4)
        assertEquals(sortedGames.value[2].score, 10)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testScoreSortDescending() = runTest {
        repo.setSortOption(SortOptions.SCORE.value)
        repo.setDescending(true)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].score, 10)
        assertEquals(sortedGames.value[1].score, 4)
        assertEquals(sortedGames.value[2].score, 1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTimePlayedSortAscending() = runTest {
        repo.setSortOption(SortOptions.TIME_PLAYED.value)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        repo.setDescending(false)
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].minutesPlayed, 42)
        assertEquals(sortedGames.value[1].minutesPlayed, 69)
        assertEquals(sortedGames.value[2].minutesPlayed, 666)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testTimePlayedSortDescending() = runTest {
        repo.setSortOption(SortOptions.TIME_PLAYED.value)
        repo.setDescending(true)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].minutesPlayed, 666)
        assertEquals(sortedGames.value[1].minutesPlayed, 69)
        assertEquals(sortedGames.value[2].minutesPlayed, 42)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testReleaseDateSortAscending() = runTest {
        repo.setSortOption(SortOptions.RELEASE_DATE.value)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        repo.setDescending(false)
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].releaseDate, 123)
        assertEquals(sortedGames.value[1].releaseDate, 312)
        assertEquals(sortedGames.value[2].releaseDate, 777)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testReleaseDateSortDescending() = runTest {
        repo.setSortOption(SortOptions.RELEASE_DATE.value)
        repo.setDescending(true)
        val sortedGames = repo.sortGames(games).stateIn(TestScope())
        advanceUntilIdle()
        assertEquals(sortedGames.value[0].releaseDate, 777)
        assertEquals(sortedGames.value[1].releaseDate, 312)
        assertEquals(sortedGames.value[2].releaseDate, 123)
    }

}