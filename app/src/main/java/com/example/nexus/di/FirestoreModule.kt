package com.example.nexus.di

import com.example.nexus.data.db.FirestoreDatabase
import com.example.nexus.data.db.FirestoreListDao
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirestoreDatabase(): FirebaseFirestore{
        return FirestoreDatabase.getInstance()
    }

//    @Provides
//    fun provideFirestoreListDao(firestoreDatabase: FirestoreDatabase): FirestoreListDao {
//        return firestoreDatabase.firestoreListDao()
//    }
}