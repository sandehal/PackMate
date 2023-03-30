package com.example.team14_turpakkeliste.ui

import ForecastData
import androidx.compose.runtime.Recomposer
import com.example.team14_turpakkeliste.data.Alert
import com.example.team14_turpakkeliste.data.Clothing

sealed interface TurpakklisteUiState {
    data class Success(val alerts: List<Alert>, val forecastData: ForecastData) : TurpakklisteUiState
    object Error : TurpakklisteUiState
    object Loading : TurpakklisteUiState
}
