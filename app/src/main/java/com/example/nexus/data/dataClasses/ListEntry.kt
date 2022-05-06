package com.example.nexus.data.dataClasses

data class ListEntry(val gameId: Long,
                     val title: String,
                     var score: Int,
                     var minutesPlayed: Int,
                     var status: String,
                     val coverUrl: String?,
                     var favorited: Boolean,
                     val releaseDate: Long)

enum class SortOptions(val value: String){
    STATUS("Status"),
    ALPHABETICALLY("Alphabetically"),
    SCORE("Score"),
    TIME_PLAYED("Time played"),
    RELEASE_DATE("Release date")
}

val SortOptionsList = listOf(SortOptions.ALPHABETICALLY.value, SortOptions.SCORE.value,
    SortOptions.TIME_PLAYED.value, SortOptions.RELEASE_DATE.value)
