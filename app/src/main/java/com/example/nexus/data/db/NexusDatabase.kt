package com.example.nexus.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListEntity::class], version = 1)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao

    companion object {

        @Volatile private var instance: NexusDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: return Room.databaseBuilder(
                    context.applicationContext, NexusDatabase::class.java, "nexus-db"
                ).build().also { instance = it }
            }

    }
}