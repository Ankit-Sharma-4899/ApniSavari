package com.taxi.apnisawari.firestoredb

data class FirestoreModelResponse(
        val item:FirestoreItem?,
        val key:String?

){

    data class FirestoreItem(
        val contact:String? = "",
        val driverAvailability:Boolean? = true,
        val latlong :String? = "",
        val name:String? = "",

    )
}


