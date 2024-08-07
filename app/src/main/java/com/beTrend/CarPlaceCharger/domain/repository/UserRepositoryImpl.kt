package com.beTrend.CarPlaceCharger.domain.repository

import com.beTrend.CarPlaceCharger.domain.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() :
    UserRepository {

    override val users = FirebaseDatabase.getInstance().reference.child("users")

    override suspend fun createUser(user: User) {
        users.child(user.id).setValue(user).await()
    }
}
