package com.beTrendM.CarPlaceCharger.presentation.sign_up

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beTrendM.CarPlaceCharger.core.Strings.LOG_TAG
import com.beTrendM.CarPlaceCharger.domain.model.Response
import com.beTrendM.CarPlaceCharger.domain.model.Passenger
import com.beTrendM.CarPlaceCharger.domain.model.UserType
import com.beTrendM.CarPlaceCharger.domain.repository.AuthRepository
import com.beTrendM.CarPlaceCharger.domain.repository.SendEmailVerificationResponse
import com.beTrendM.CarPlaceCharger.domain.repository.SignUpResponse
import com.beTrendM.CarPlaceCharger.domain.repository.UserRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository,
) : ViewModel() {
    var signUpResponse by mutableStateOf<SignUpResponse>(Response.None)
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Response.None)
        private set

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = authRepo.sendEmailVerification()
    }

    fun registerAndCreateUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        userType: UserType,
        localImageUri: String,
    ) = viewModelScope.launch {
        try {
            signUpResponse = Response.Loading
            when (val response =
                authRepo.firebaseSignUpWithEmailAndPasswordAsync(email, password)) {
                is Response.Failure -> Unit
                is Response.Loading -> Unit
                is Response.Success -> {
                    Log.i(LOG_TAG, "enviando foto para o armazenamento do Firebase")
                    val imageUrl =
                        uploadPhotoToFirebaseStorageAsync(Uri.parse(localImageUri)).await()

                    val user = when (userType) {
                        UserType.Pessoal -> Passenger(
                            response.data.user!!.uid,
                            email,
                            firstName,
                            lastName,
                            phone,
                            imageUrl,
                            userType,
                            0.0,
                            0.0,
                            0
                        )
                    }

                    userRepo.createUser(user)
                    signUpResponse = response
                }

                is Response.None -> Unit
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.toString())
        }
    }

    private suspend fun uploadPhotoToFirebaseStorageAsync(uri: Uri) = viewModelScope.async {
        val photoRef = Firebase.storage.reference.child("foto/${uri.lastPathSegment}")

        photoRef.putFile(uri).await()

        val downloadUri = photoRef.downloadUrl.await()

        return@async downloadUri.toString()
    }
}
