package com.beTrendM.CarPlaceCharger.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beTrendM.CarPlaceCharger.core.Strings.LOG_TAG
import com.beTrendM.CarPlaceCharger.core.Utils.Companion.snapshotToUser
import com.beTrendM.CarPlaceCharger.domain.model.SearchFilters
import com.beTrendM.CarPlaceCharger.domain.model.User
import com.beTrendM.CarPlaceCharger.domain.repository.AuthRepository
import com.beTrendM.CarPlaceCharger.domain.repository.UserRepository
import com.google.android.gms.location.LocationResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userRepo: UserRepository, val authRepo: AuthRepository
) : ViewModel() {
    var usersOnMap = MutableStateFlow<MutableList<User>>(mutableListOf())
    var currentUser = MutableStateFlow<User?>(null)

    val showRatingDialog = MutableStateFlow<Boolean>(false)

    private val currentUserListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            // snapshot value may be null
            if (snapshot.value != null) {
                Log.i(LOG_TAG, snapshot.value.toString())
                currentUser.value = snapshotToUser(snapshot)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(LOG_TAG, "usersListener:onCancelled", error.toException())
        }
    }

    private val passengerInTransitListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            // snapshot value may be null
            if (snapshot.value != null) {
                val inTransit = snapshot.value as Boolean

                if (!inTransit) {
                    Log.i(LOG_TAG, "Time for ratings")
                    showRatingDialog.update { true }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.w(LOG_TAG, "usersListener:onCancelled", error.toException())
        }
    }

    init {
        loadCurrentUser()
        userRepo.users.child(authRepo.currentUser!!.uid).addValueEventListener(currentUserListener)

        userRepo.users.child(authRepo.currentUser!!.uid).child("inTransit")
            .addValueEventListener(passengerInTransitListener)
    }

    override fun onCleared() {
        super.onCleared()
        userRepo.users.child(authRepo.currentUser!!.uid).removeEventListener(currentUserListener)
    }

    fun updateUserLocation(location: LocationResult) {
        val userId = authRepo.currentUser?.uid
        if (userId != null) {
            userRepo.users.child(userId).child("latitude")
                .setValue(location.lastLocation!!.latitude)
            userRepo.users.child(userId).child("longitude")
                .setValue(location.lastLocation!!.longitude)
        }
    }

    private fun loadCurrentUser() = viewModelScope.launch {
        val res = userRepo.users.child(authRepo.currentUser!!.uid).get().await()

        currentUser.value = snapshotToUser(res)
    }

    fun loadFilteredUsers(searchFilters: SearchFilters) = viewModelScope.launch {
        val currentUserSnapshot = userRepo.users.child(authRepo.currentUser!!.uid).get().await()

        Log.i(LOG_TAG, "Filtrando")

        val currentUser: User = snapshotToUser(currentUserSnapshot)

        val usersSnapshot = userRepo.users.get().await()

        val usersToFilter = usersSnapshot.children.mapNotNull {
            Log.i(LOG_TAG, it.toString())
            snapshotToUser(it)
        }
    }
}


