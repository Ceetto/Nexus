package com.example.nexus.di

import com.example.nexus.data.db.Storage
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Singleton
    @Provides
    fun provideStorage(): FirebaseStorage {
        return Storage.getInstance()
    }
}