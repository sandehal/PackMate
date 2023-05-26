package com.example.team14_turpakkeliste.ui.viewModel

import com.example.team14_turpakkeliste.data.models.ForecastData
import com.example.team14_turpakkeliste.data.models.Alert

sealed interface TurpakklisteUiState {
    data class Success(val alerts: List<Alert>, var forecastData: ForecastData) :
        TurpakklisteUiState
    object Error : TurpakklisteUiState

    object OfflineMode : TurpakklisteUiState
    object Loading : TurpakklisteUiState

    object Booting : TurpakklisteUiState
}