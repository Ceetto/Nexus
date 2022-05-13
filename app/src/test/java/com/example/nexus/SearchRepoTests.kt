package com.example.nexus

import com.example.nexus.data.repositories.gameData.SearchRepository
import org.junit.Assert
import org.junit.Test
import proto.Game

class SearchRepoTests {

    @Test
    fun whenEmptyingListShouldEmptyGameList(){
        val repo = SearchRepository()
        repo.gameList.value.value = arrayListOf(Game.getDefaultInstance(), Game.getDefaultInstance(), Game.getDefaultInstance())
        Assert.assertTrue(repo.gameList.value.value.isNotEmpty())
        repo.emptyList()
        Assert.assertTrue(repo.gameList.value.value.isEmpty())
    }

    @Test
    fun whenLoadingMoreShouldIncreaseGamesToLoad(){
        val repo = SearchRepository()
        Assert.assertEquals(repo.toLoad.value.value, 10)
        repo.loadMore()
        Assert.assertEquals(repo.toLoad.value.value, 20)
    }
}