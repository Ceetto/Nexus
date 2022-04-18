package com.example.nexus.di

import com.example.nexus.data.web.ListBackend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ListBackendModule {

    @Singleton
    @Provides
    fun provideListBackend(): ListBackend{
        return ListBackend()
    }
}