package com.beTrend.CarPlaceCharger.presentation.station

import android.content.Intent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beTrend.CarPlaceCharger.ui.theme.BackCardD
import com.beTrend.CarPlaceCharger.ui.theme.BackCardL
import com.beTrend.CarPlaceCharger.ui.theme.Graffit
import com.beTrend.CarPlaceCharger.ui.theme.Green50
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily
import com.google.android.gms.maps.MapsInitializer


// Modificador de Item de Da Lista
@Composable
fun StationListItem(station: Station) {

    val context = LocalContext.current

    // Criar o gradient usando uma lista com as cores e passando para o brush
    val colors = listOf(BackCardL, BackCardD)
    val brush = Brush.verticalGradient(colors)

    // Configurando a expansão do item
    var expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 25.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // Card Item
    Card(
        modifier = Modifier
            //.paint(painterResource(id = R.drawable.back_item))
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        //colors = CardDefaults.cardColors(BackCardL),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
        ),
        border = BorderStroke(1.dp, BackCardD),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .clickable {
                    // Expandir ao clicar
                    expanded.value = !expanded.value
                },
        ) {
            Row(
                //Modifier.clickable { navigateToProfile(station) }
            ) {
                StationImage(station = station)         // Imagem do card
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        color = Green50,
                        text = station.name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = sourceProFontFamily
                        )
                    )
                }
            }

            // Item a visualizar com Item Expandido
            if (expanded.value) {
                Column(
                    modifier = Modifier
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                        .clickable {

                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.weight(1f),
                                color = Graffit,
                                text = station.desc,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = sourceProFontFamily
                                ),
                                maxLines = 2
                            )
                            Button(
                                onClick = {
                                    // Quando o item é expandido, clicando no Button, muda para a Tela do Mapa
                                    val i = Intent(context, MapsActivity::class.java).apply {
                                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        putExtra("name", station.name)
                                        putExtra("desc", station.desc)
                                        putExtra("lat", station.latd)
                                        putExtra("long", station.long)
                                    }
                                    context.startActivity(i)
                                },
                                colors = ButtonDefaults.buttonColors(Green50),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(3.dp)
                                    .offset(5.dp, 5.dp)
                            ) {
                                Text(
                                    text = "Mapa",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = sourceProFontFamily
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Imagem do CARD
@Composable
private fun StationImage(station: Station) {
    Image(
        painter = painterResource(id = station.img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp))),
        alignment = Alignment.Center
    )
}