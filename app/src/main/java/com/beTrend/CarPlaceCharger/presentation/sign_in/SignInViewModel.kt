package com.beTrendM.CarPlaceCharger.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beTrendM.CarPlaceCharger.domain.model.Response
import com.beTrendM.CarPlaceCharger.domain.repository.AuthRepository
import com.beTrendM.CarPlaceCharger.domain.repository.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signInResponse by mutableStateOf<SignInResponse>(Response.None)
        private set

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password)
    }
    init {
        if (repo.isAuthenticated(viewModelScope).value) {
            signInResponse = Response.Success(true)
        }
    }
}
