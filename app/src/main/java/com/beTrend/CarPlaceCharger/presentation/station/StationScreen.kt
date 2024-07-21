package com.beTrend.CarPlaceCharger.presentation.station

import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Preview
@Composable
fun StationScreen() {
    val stationList = remember { DataProvider.stationList }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        stationList.forEach { station ->
            StationListItem(station = station)
        }
    }
}
