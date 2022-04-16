package com.example.nexus.data.web

import com.example.nexus.ui.routes.list.ListCategory

data class ListEntry(val gameId: Int,
                     val title: String,
                     val score: Int,
                     val hoursPlayed: Int,
                     val status: ListCategory)

// made for testing purposes
class ListBackend{
    private val games = listOf(
        ListEntry(123, "game0", 10, 69, ListCategory.ALL),
        ListEntry(123, "game1", 10, 69, ListCategory.ALL),
        ListEntry(123, "game2", 10, 69, ListCategory.ALL),
        ListEntry(123, "game3", 10, 69, ListCategory.ALL),
        ListEntry(123, "game4", 10, 69, ListCategory.ALL),
        ListEntry(123, "game5", 10, 69, ListCategory.ALL),
        ListEntry(123, "game6", 10, 69, ListCategory.ALL),
        ListEntry(123, "game7", 10, 69, ListCategory.ALL),
        ListEntry(123, "game8", 10, 69, ListCategory.ALL),
        ListEntry(123, "game9", 10, 69, ListCategory.ALL),
        ListEntry(123, "game10", 10, 69, ListCategory.ALL),
    )

    fun getGames() : List<ListEntry>{
        return games
    }
}