package com.beTrendM.CarPlaceCharger.domain.repository

import com.beTrendM.CarPlaceCharger.domain.model.User
import com.google.firebase.database.DatabaseReference

interface UserRepository {
    val users: DatabaseReference
    suspend fun createUser(user: User)

}