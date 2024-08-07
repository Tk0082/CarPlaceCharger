package com.beTrend.CarPlaceCharger.domain.repository

import com.beTrend.CarPlaceCharger.domain.model.User
import com.google.firebase.database.DatabaseReference

interface UserRepository {
    val users: DatabaseReference
    suspend fun createUser(user: User)

}