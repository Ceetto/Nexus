package com.example.nexus.data.db

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
abstract class Database {

    companion object{
        fun getInstance(): FirebaseDatabase {
            return Firebase.database("https://nexus-f7d65-default-rtdb.europe-west1.firebasedatabase.app")
        }
    }
}