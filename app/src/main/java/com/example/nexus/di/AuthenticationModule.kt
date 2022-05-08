package com.example.nexus.di

import com.example.nexus.data.Authentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth


@InstallIn(SingletonComponent::class)
@Module
class AuthenticationModule {

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth {
        return Authentication.getAuth()
    }

}