package com.betrendmobile.carplacecharger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrendmobile.carplacecharger.data.DataProvider
import com.betrendmobile.carplacecharger.ui.theme.Green50
import com.betrendmobile.carplacecharger.ui.theme.Green80
import com.betrendmobile.carplacecharger.ui.theme.PowerUpTheme
import com.betrendmobile.carplacecharger.ui.theme.sourceProFontFamily
import com.betrendmobile.carplacecharger.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerUpTheme {
                PowerAppView ()
            }
        }
    }

    @Preview
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun PowerAppView() {
        Scaffold (
            modifier = Modifier.fillMaxSize()
        ){
            Column {
                TopBar()
                StationsContent()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Composable
    fun TopBar() {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Green50)
                .offset(10.dp, 5.dp)
                .height(65.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.padding(5.dp))
            Image(
                painter = painterResource(id = R.mipmap.ico_energy),
                contentDescription = null,
                Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(horizontal = 3.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                color = Green80,
                text = "PowerUp",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sourceProFontFamily
                )
            )
        }
    }

    // Parametro passado para a ação de click levar à próxima activity
    @Composable
    fun StationsContent(){
        // stations recebe o DataProvider com a lista
        val stations = remember { DataProvider.stationList }
        LazyColumn (
            contentPadding = PaddingValues(vertical = 5.dp, horizontal = 8.dp)
        ){
            items(
                items = stations,  // itens recebe as estações do DB
            ){
                StaionListItem(station = it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }
}
