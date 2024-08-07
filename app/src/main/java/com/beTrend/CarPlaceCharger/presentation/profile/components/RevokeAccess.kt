package com.beTrend.CarPlaceCharger.presentation.profile.components

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.ProgressBar
import com.beTrend.CarPlaceCharger.core.Strings.ACCESS_REVOKED_MESSAGE
import com.beTrend.CarPlaceCharger.core.Strings.REVOKE_ACCESS_MESSAGE
import com.beTrend.CarPlaceCharger.core.Strings.SENSITIVE_OPERATION_MESSAGE
import com.beTrend.CarPlaceCharger.core.Strings.SIGN_OUT_ITEM
import com.beTrend.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrend.CarPlaceCharger.domain.model.Response
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.beTrend.CarPlaceCharger.presentation.profile.ProfileViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember {SnackbarHostState()},
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT_ITEM
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.None -> {}
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showToast(context, ACCESS_REVOKED_MESSAGE)
                }
            }
        }
        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}