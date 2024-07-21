package com.beTrendM.CarPlaceCharger.presentation.sign_up.components

import CameraCapture
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.beTrendM.CarPlaceCharger.components.EmailField
import com.beTrendM.CarPlaceCharger.components.PasswordField
import com.beTrendM.CarPlaceCharger.components.SmallSpacer
import com.beTrendM.CarPlaceCharger.components.TextField
import com.beTrendM.CarPlaceCharger.core.Strings.ALREADY_USER
import com.beTrendM.CarPlaceCharger.core.Strings.SIGN_UP_BUTTON
import com.beTrendM.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrendM.CarPlaceCharger.domain.model.UserType

@Composable
@ExperimentalComposeUiApi
fun SignUpContent(
    padding: PaddingValues,
    signUpPassenger: (
        email: String,
        password: String,
        phone: String,
        firstName: String,
        lastName: String,
        userType: UserType,
        photoUri: String
    ) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

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
                    text = "SuaSenha"
                )
            )
        }
    )
    val keyboard = LocalSoftwareKeyboardController.current

    var phone by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = "98143-7944"
                )
            )
        }
    )

    var firstName by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = "Sabrina"
                )
            )
        }
    )

    var lastName by rememberSaveable(
        stateSaver = TextFieldValue.Saver,
        init = {
            mutableStateOf(
                value = TextFieldValue(
                    text = "Campos"
                )
            )
        }
    )

    var userType by rememberSaveable(
        init = {
            mutableStateOf(
                value = UserType.Pessoal
            )
        }
    )

    var imageUri: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailField(
            email = email,
            onEmailValueChange = { newValue -> email = newValue }
        )
        SmallSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue -> password = newValue }
        )
        SmallSpacer()
        TextField(
            text = firstName,
            keyboardType = KeyboardType.Text,
            onTextValueChange = { newValue -> firstName = newValue },
            label = "Primeiro Nome"
        )
        SmallSpacer()
        TextField(
            text = lastName,
            keyboardType = KeyboardType.Text,
            onTextValueChange = { newValue -> lastName = newValue },
            label = "Último Nome"
        )
        SmallSpacer()
        TextField(
            text = phone,
            keyboardType = KeyboardType.Phone,
            onTextValueChange = { newValue -> phone = newValue },
            label = "Número para contato"
        )
        SmallSpacer()
        Text(text = "Tipo de Conta")
        SmallSpacer()
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = userType == UserType.Pessoal,
                onClick = { userType = UserType.Pessoal }
            )
            Text(text = UserType.Pessoal.name)
            SmallSpacer()
        }
        SmallSpacer()
        CameraCapture { uri -> imageUri = uri.toString() }
        SmallSpacer()

        var seats by rememberSaveable(
            stateSaver = TextFieldValue.Saver,
            init = {
                mutableStateOf(
                    value = TextFieldValue(
                        text = "5"
                    )
                )
            }
        )
        Button(
            onClick = {
                if (imageUri.isNullOrBlank()) {
                    showToast(context, "Tire sua Foto antes")
                    return@Button
                }

                keyboard?.hide()

                when (userType) {
                    UserType.Pessoal -> signUpPassenger(
                        email.text,
                        password.text,
                        phone.text,
                        firstName.text,
                        lastName.text,
                        userType,
                        imageUri!!
                    )
                }


            }
        ) {
            Text(
                text = SIGN_UP_BUTTON,
                fontSize = 15.sp
            )
        }
        Text(
            modifier = Modifier.clickable {
                navigateBack()
            },
            text = ALREADY_USER,
            fontSize = 15.sp
        )
    }
}
