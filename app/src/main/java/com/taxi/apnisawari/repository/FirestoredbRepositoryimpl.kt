package com.taxi.apnisawari.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.taxi.apnisawari.ResultState
import com.taxi.apnisawari.firestoredb.FirestoreModelResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoredbRepositoryimpl @Inject constructor(
    private val db: FirebaseFirestore
) : FirestoreRepository {
    override fun insert(item: FirestoreModelResponse.FirestoreItem): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.collection("Driver")
                .add(item)
                .addOnSuccessListener {
                    trySend(ResultState.Success("data inserted with id ${it.id}"))
                }
                .addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<ResultState<List<FirestoreModelResponse>>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("Driver")
            .get()
            .addOnSuccessListener {
                val details = it.map { data ->
                    FirestoreModelResponse(
                        item = FirestoreModelResponse.FirestoreItem(
                            contact = data["contact"] as String?,
                            latlong = data["latlong"] as String?,
                            name = data["name"] as String?,
                            driverAvailability = data["driverAvailability"] as Boolean?

                        ),
                        key = data.id

                    )
                }
                trySend(ResultState.Success(details))
            }.addOnFailureListener{
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override fun update(item: FirestoreModelResponse): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }
}