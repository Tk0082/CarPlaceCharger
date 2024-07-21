package com.beTrendM.CarPlaceCharger.presentation.leaderboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beTrend.CarPlaceCharger.presentation.station.DataProvider
import com.beTrend.CarPlaceCharger.presentation.station.Station
import com.beTrendM.CarPlaceCharger.core.Utils.Companion.snapshotToUser
import com.beTrendM.CarPlaceCharger.domain.model.User
import com.beTrendM.CarPlaceCharger.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class StateListViewModel @Inject constructor(
    private val userRepo: UserRepository

) : ViewModel() {
    val station = MutableStateFlow<MutableList<User>>(mutableListOf())

    init {
        loadState()
    }

    private fun loadState() = viewModelScope.launch {
        val usersSnapshot = userRepo.users.get().await()

        station.update {
            usersSnapshot.children.mapNotNull {
                snapshotToUser(it)
            }.toMutableStateList()
        }

        station.value.sortByDescending { it.score }
    }
}

class StationViewModel : ViewModel() {
    // Lista de estações como um estado
    var stationList = mutableStateListOf<Station>()
        private set

    init {
        // Inicialize a lista com dados do DataProvider
        stationList.addAll(DataProvider.stationList)
    }
}