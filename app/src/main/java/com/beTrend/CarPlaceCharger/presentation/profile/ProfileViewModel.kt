package com.beTrend.CarPlaceCharger.presentation.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beTrend.CarPlaceCharger.core.Strings.LOG_TAG
import com.beTrend.CarPlaceCharger.core.Utils.Companion.snapshotToUser
import com.beTrend.CarPlaceCharger.domain.model.User
import com.beTrend.CarPlaceCharger.domain.model.Response
import com.beTrend.CarPlaceCharger.domain.repository.AuthRepository
import com.beTrend.CarPlaceCharger.domain.repository.ReloadUserResponse
import com.beTrend.CarPlaceCharger.domain.repository.RevokeAccessResponse
import com.beTrend.CarPlaceCharger.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val userRepo: UserRepository

) : ViewModel() {
    var user = MutableStateFlow<User?>(null)
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.None)
        private set
    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Response.None)
        private set

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        val res = userRepo.users.child(authRepo.currentUser!!.uid).get().await()

        Log.i(LOG_TAG, res.value.toString())

        user.update {  snapshotToUser(res)}
        Log.i(LOG_TAG, "Perfil " + user.toString())
    }

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Response.Loading
        reloadUserResponse = authRepo.reloadFirebaseUser()
    }

    val isEmailVerified get() = authRepo.currentUser?.isEmailVerified ?: false

    fun signOut() = authRepo.signOut()
}