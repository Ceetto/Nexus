package com.example.nexus.data.repositories

import android.util.Log
import com.example.nexus.data.db.FirestoreDatabase
import com.example.nexus.data.db.FirestoreListDao
import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.web.ListBackend
import com.example.nexus.data.web.ListEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class ListRepository @Inject constructor(
    private val backend: ListBackend,
    private val dao: ListDao,
    private val firestoreDao: FirestoreListDao
) {

    private val database = FirebaseFirestore.getInstance()
//    private val database = Firebase.firestore

    private val listEntryReference = database.collection("listentry")

    private val TAG = "ListRepository"

    fun getBackendGames() = backend.getGames()

    suspend fun storeListEntry(entry: ListEntry) = dao.storeListEntry(entry)

    suspend fun deleteListEntry(entity: ListEntity) = dao.deleteListEntry(entity)

    fun getCategory(category: String) = dao.getCategory(category)

    suspend fun wipeDatabase() = dao.wipeDatabase()

    val allGames = dao.getAll()

    val playing = dao.getPlaying()

    val completed = dao.getCompleted()

    val planned = dao.getPlanned()

    val dropped = dao.getDropped()

    suspend fun getTest(): Flow<ArrayList<ListEntry>> {
        val firestoreTest = listEntryReference
            .get()
            .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
            .await()
        val entries = ArrayList<ListEntry>()
        for (document in firestoreTest.documents){
            entries.add(ListEntry(
                document.getLong("gameId")!!, document.getString("title")!!,
                document.getLong("score")!!.toInt(), document.getLong("minutesPlayed")!!.toInt(),
                document.getString("status")!!, document.getString("coverUrl")
            ))
        }
        return MutableStateFlow(entries)
    }

}