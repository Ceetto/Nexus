package com.example.nexus.data.dataClasses

data class ListEntry(val gameId: Long,
                     val title: String,
                     val score: Int,
                     val minutesPlayed: Int,
                     val status: String,
                     val coverUrl: String?,
                     val favorited: Boolean)
