package com.beTrend.CarPlaceCharger.presentation.sign_in.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.beTrend.CarPlaceCharger.components.EmailField
import com.beTrend.CarPlaceCharger.components.PasswordField
import com.beTrend.CarPlaceCharger.components.SmallSpacer
import com.beTrend.CarPlaceCharger.core.Strings.NO_ACCOUNT
import com.beTrend.CarPlaceCharger.core.Strings.SIGN_IN_BUTTON

@Composable
@ExperimentalComposeUiApi
fun SignInContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    navigateToSignUpScreen: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = "SeuEmail@gmail.com"
                )
            )
        }
    )
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = "Sua senha"
                )
            )
        }
    )
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        SmallSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            }
        )
        SmallSpacer()
        Button(
            onClick = {
                keyboard?.hide()
                signIn(email.text, password.text)
            }
        ) {
            Text(
                text = SIGN_IN_BUTTON,
                fontSize = 15.sp
            )
        }
        SmallSpacer()
        Row {
            Text(
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                },
                text = NO_ACCOUNT,
                fontSize = 15.sp
            )
        }
    }
}