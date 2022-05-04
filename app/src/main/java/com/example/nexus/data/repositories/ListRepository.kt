package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.web.ListBackend
import com.example.nexus.data.web.ListEntry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val backend: ListBackend,
    private val dao: ListDao,
    private val firebaseListDao: FirebaseListDao
) {

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

//    suspend fun getTest(): Flow<ArrayList<ListEntry>> {
//        val firestoreTest = listEntryReference
//            .get()
//            .addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d(TAG, "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents.", exception)
//            }
//            .await()
//        val entries = ArrayList<ListEntry>()
//        for (document in firestoreTest.documents){
//            entries.add(ListEntry(
//                document.getLong("gameId")!!, document.getString("title")!!,
//                document.getLong("score")!!.toInt(), document.getLong("minutesPlayed")!!.toInt(),
//                document.getString("status")!!, document.getString("coverUrl")
//            ))
//        }
//        return MutableStateFlow(entries)
//    }

    fun writeTest() = firebaseListDao.writeTest()


}