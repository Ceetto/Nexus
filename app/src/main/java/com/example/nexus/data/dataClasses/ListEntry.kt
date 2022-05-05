package com.example.nexus.data.dataClasses

data class ListEntry(val gameId: Long,
                     val title: String,
                     var score: Int,
                     var minutesPlayed: Int,
                     var status: String,
                     val coverUrl: String?,
                     var favorited: Boolean)
