package com.example.nexus.di

import android.content.Context
import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.NexusDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNexusDatabase(@ApplicationContext context: Context): NexusDatabase {
        return NexusDatabase.getInstance(context)
    }

    @Provides
    fun provideListDao(nexusDatabase: NexusDatabase): ListDao {
        return nexusDatabase.listDao()
    }
}