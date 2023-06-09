package com.taxi.apnisawari.repository

import com.taxi.apnisawari.ResultState
import com.taxi.apnisawari.firestoredb.FirestoreModelResponse
import kotlinx.coroutines.flow.Flow


interface FirestoreRepository {

    fun insert(
        item: FirestoreModelResponse.FirestoreItem
        ): Flow<ResultState<String>>

    fun getItems():Flow<ResultState<List<FirestoreModelResponse>>>

    fun delete(key:String):Flow<ResultState<String>>

    fun update(
        item: FirestoreModelResponse
        ):Flow<ResultState<String>>
}