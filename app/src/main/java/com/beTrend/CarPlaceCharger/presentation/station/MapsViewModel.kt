package com.beTrend.CarPlaceCharger.presentation.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MapsViewModel : ViewModel() {

    private val apiKey = "AIzaSyDQl09-TeRXqyPIg6h0G9LQYFPocyNIGsM"      // "AIzaSyDQl09-TeRXqyPIg6h0G9LQYFPocyNIGsM"   "AIzaSyCL19tX53SVo_P4YYdHgEZ0QhVmXAX79as"

    fun getRoute(origin: String, destination: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDirections(origin, destination, apiKey)
                if (response.routes.isNotEmpty()) {
                    val points = response.routes[0].overviewpolyline.points
                    onSuccess(points)  // Envie os pontos para a UI
                }
            } catch (e: Exception) {
                e.printStackTrace()  // Trate o erro adequadamente
            }
        }
    }
}
