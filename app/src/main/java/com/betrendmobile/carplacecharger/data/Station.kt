package com.betrendmobile.carplacecharger.data

data class Station (
    var id: Int,
    var img: Int = 0,
    var latd: Double = 0.0,
    var long: Double = 0.0,
    var name: String,
    var desc: String
)