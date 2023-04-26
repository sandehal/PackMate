package com.example.team14_turpakkeliste.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.EntityClass.AppDatabase
import com.example.team14_turpakkeliste.data.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException


class TurViewModel: ViewModel() {
    var error : String? = null
    var currentLatitude : Double = 0.0
    var currentLongitude : Double = 0.0
    //denne er litt goofy når man velger antall dager fordi det går på indeksering fra 0 til 2!!!
    var numberOfDays : Int = 2
    var chosenDay: Int = 0
    lateinit var outerLayerList: List<Clothing>
    lateinit var innerLayerList: List<Clothing>
    lateinit var weatherInfo: WeatherValues
    lateinit var weatherImg: String
    lateinit var alertList: List<Alert>

    var currentLatitudeLongitude = MutableLiveData<Pair<Double, Double>>()

    var turUiState: TurpakklisteUiState by mutableStateOf(TurpakklisteUiState.Booting)
        private set
    private val source: Datasource = Datasource()

    init {
        getData()
    }
    fun getForecast(alerts:List<Alert>){
        viewModelScope.launch {
            val forecast = source.getForecastData(currentLatitude, currentLongitude)
            turUiState = TurpakklisteUiState.Success(alerts, forecast)
        }
    }

    private fun getData() {
        viewModelScope.launch {
            turUiState = try {
                val alertList = source.getAllAlerts()
                val forecast = source.getForecastData(currentLatitude,currentLongitude)
                TurpakklisteUiState.Success(alertList, forecast)
            } catch (ex: ResponseException) {
                error = ex.toString()
                TurpakklisteUiState.Error
            } catch (ex: SerializationException) {
                error = ex.toString()
                TurpakklisteUiState.Error
            } catch(e:Throwable){
                error = e.toString()
                TurpakklisteUiState.Error
            }
        }
    }
}
