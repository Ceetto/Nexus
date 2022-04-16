package com.example.nexus.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nexus.ui.routes.list.ListCategory

@Entity(tableName = "list")
data class ListEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val gameId: Int,
    val title: String,
    val score: Int,
    val hoursPlayed: Int,
    val status: String
)