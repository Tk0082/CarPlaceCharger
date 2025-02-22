package com.beTrend.CarPlaceCharger.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.ui.theme.BlueApp
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily

@Composable
fun ConectorItemList(conector: Conector) {

    var showDialog by remember { mutableStateOf(false) }

    val conectores = listOf(conector.name)
    var selectedOption by remember { mutableStateOf(conectores.first()) }

    conectores.forEach {conect ->

            Image(
                painter = painterResource(
                    id = R.drawable.ic_info
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                    .clickable {
                        showDialog = true
                    },
                alignment = Alignment.Center
            )
    }

    // Exibe o AlertDialog se showDialog for true
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 30.dp, horizontal = 40.dp)
                .widthIn(max = 200.dp)
                .verticalScroll(state = ScrollState(0), true),
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = conector.name,
                    fontFamily = sourceProFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = BlueApp
                )
            },
            text = {
                Text(
                    modifier = Modifier.verticalScroll(state = ScrollState(0), true),
                    text = conector.desc,
                )
            },
            icon = { ConectorImage(conector = conector) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                ) {
                    Text("Fechar")
                }
            }
        )
    }
}

@Composable
private fun ConectorImage(conector: Conector) {
    Image(
        painter = painterResource(id = conector.img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(450.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp))),
        alignment = Alignment.Center
    )
}