package com.example.nexus.di

import com.example.nexus.data.db.Database
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class FirestoreModule {

    @Singleton
    @Provides
    fun provideFirestoreDatabase(): FirebaseDatabase{
        return Database.getInstance()
    }

//    @Provides
//    fun provideFirestoreListDao(firestoreDatabase: FirestoreDatabase): FirestoreListDao {
//        return firestoreDatabase.firestoreListDao()
//    }
}