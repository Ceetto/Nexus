package com.example.nexus

import com.example.nexus.data.db.FirestoreDatabase
import com.example.nexus.data.db.FirestoreListDao
import com.example.nexus.data.repositories.ListRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@HiltAndroidTest
class FirestoreTests {
    private lateinit var database: FirebaseFirestore

    @Before
    fun initDb(){
        database = FirebaseFirestore.getInstance()
    }

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}