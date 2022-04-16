package com.example.nexus.data.web

import com.example.nexus.ui.routes.list.ListCategory

data class ListEntry(val gameId: Int,
                     val title: String,
                     val score: Int,
                     val hoursPlayed: Int,
                     val status: ListCategory)