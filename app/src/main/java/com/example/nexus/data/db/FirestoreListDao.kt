package com.example.nexus.data.db

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreListDao @Inject constructor(
    private val firestore: FirebaseFirestore
){
    private val listEntryRef = firestore.collection("listentry")

    fun getAll(): StateFlow<QuerySnapshot?> {
        return MutableStateFlow(listEntryRef.get().result)
    }
}