@file:Suppress("DEPRECATION", "DEPRECATION")

package com.example.team14_turpakkeliste.ui.viewModel



import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.data.*
import com.example.team14_turpakkeliste.data.models.Alert
import com.example.team14_turpakkeliste.data.models.Clothing
import com.example.team14_turpakkeliste.data.models.WeatherValues
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.IOException

class TurViewModel: ViewModel() {
    var isOffline : Boolean = false
    var prevScreen : String = "SavedScreen"
    var error : String? = null

    var currentLatitude : Double = 59.9
    var currentLongitude : Double = 10.7
    private lateinit var alerts: List<Alert>
    var numberOfDays : Int = 0
    var chosenDay: Int = 0
    lateinit var outerLayerList: List<Clothing>
    lateinit var innerLayerList: List<Clothing>
    lateinit var weatherInfo: WeatherValues
    lateinit var weatherImg: String
    lateinit var location: String

    var turUiState: TurpakklisteUiState by mutableStateOf(TurpakklisteUiState.Booting)
        private set
    private val source: Datasource = Datasource()
    init {
        getData()
    }

    /**
     * Funksjonen sjekker om location variabelen er initialiser. Hvis ikke returnerer den koordinater.
     * */
    fun checkIntitialized(): String{
        return if(this::location.isInitialized){
            location
        } else{
            "${currentLatitude}, $currentLongitude"
        }
    }

    /**
     * Funksjonen oppdaterer antall dager til det som er valgt i bottom sheeten.
     * */
    fun updateDays(days: Int){

        numberOfDays = days

    }

    /**
     * Funksjonen gjør et api-kall mot locationForecast og setter data inn i UiState.
     * */
    fun getForecast(){
        viewModelScope.launch {

            turUiState = try {
                val forecast = source.getForecastData(currentLatitude, currentLongitude)
                TurpakklisteUiState.Success(alerts, forecast)
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

    /**
     * Funksjonen returnerer en triple med beskrivelse av alerten.
     * */
    fun getAlertDataForArea(): Triple<String, String, String>?{
        var alertColor = "green"
        var alertType = ""
        var alertdescription = ""
        for(alert in alerts){
            if(pinpointLocation(currentLatitude,currentLongitude,alert.areaPolygon!!)){
                val string= alert.awareness_level?.split(";")
                val awarenesslevel = string?.get(1)?.trim()
                val typeString = alert.awareness_type?.split(";")
                val typeStringsplit1 = typeString?.get(1)?.split("-")
                val awarenesstype = typeStringsplit1?.get(0)?.trim()
                if(awarenesslevel == "red"){
                    alertColor = "red"
                    alertType = awarenesstype.toString()
                    alertdescription = alert.description.toString()
                    break
                }
                //warningen som dukker opp her stemmer ikke. AlertColor != "red" kan bli false.
                if(awarenesslevel == "orange"){
                    alertColor = "orange"
                    alertType = awarenesstype.toString()
                    alertdescription = alert.description.toString()
                }
                if(awarenesslevel == "yellow" && alertColor == "green"){
                    alertColor = "yellow"
                    alertType = awarenesstype.toString()
                    alertdescription = alert.description.toString()
                }
            }
        }
        return if (alertColor == "green"){
            null
        } else {
            Triple(alertType, alertColor, alertdescription)
        }
    }

    /**
     * Funksjonen setter data fra API inn i viewMode og oppdaterer UIstaten.
     * */
    private fun getData() {
        viewModelScope.launch {
            turUiState = try {
                val alertList = source.getAllAlerts()
                alerts = alertList
                val forecast = source.getForecastData(currentLatitude,currentLongitude)
                TurpakklisteUiState.Success(alertList, forecast)
            } catch (ex: ResponseException) {
                error = ex.toString()
                isOffline = true
                TurpakklisteUiState.OfflineMode
            } catch (ex: SerializationException) {
                error = ex.toString()
                isOffline = true
                TurpakklisteUiState.OfflineMode
            } catch(e:Throwable){
                error = e.toString()
                isOffline = true
                TurpakklisteUiState.OfflineMode
            }
        }
    }


    /**
     * Funksjonen setter location til stedsnavn basert på koordinater den får inn som parametre.
     * */
     fun getNameFromLocation(cordinates: LatLng, context: Context){

        var addressList : List<Address>? = null
        val geocoder = Geocoder(context)

        viewModelScope.launch {

            try {

                addressList = geocoder.getFromLocation(cordinates.latitude, cordinates.longitude, 1)

            } catch (e: IOException) {
                e.printStackTrace()
                error = e.toString()
                turUiState = TurpakklisteUiState.Error
            }

             if (!addressList.isNullOrEmpty()) {
                val address = addressList!![0]
                currentLatitude = address.latitude
                currentLongitude = address.longitude

                location = checkAvailabilityLoc(address)


            } else if (addressList == null) {
                error = "Får ikke tak i data."
                turUiState = TurpakklisteUiState.Error
            } else{
                 location = "Nå er du på bærtur!"
             }
        }

    }

    /**
     * Funksjonen setter viewmodel sin latitude and longitude basert på lokasjonen den får som parameter
     * */
    fun getLocationCompose(location1: String, context: Context) {
        location = location1


        var addressList : List<Address>? = null

        viewModelScope.launch {
            val geocoder = Geocoder(context)
            try {

                addressList = geocoder.getFromLocationName(location.plus(", Norway"), 1)

            } catch (e: IOException) {
                e.printStackTrace()
                error = e.toString()
                turUiState = TurpakklisteUiState.Error

            }


            if (addressList != null && addressList!!.isNotEmpty()) {
                val address = addressList!![0]
                currentLatitude = address.latitude
                currentLongitude = address.longitude


            } else if(addressList == null){
                error = "Får ikke tak i data."
                turUiState = TurpakklisteUiState.Error
            }
            else{
                location = "Nå er du på bærtur!"
            }
        }

    }


    /**
     * Funksjonen returner den mest nøyaktige addressen basert på address-objektet den får som parameter.
     * */
    private fun checkAvailabilityLoc(address: Address): String{

        if(address.subLocality != null){
            return if (address.subLocality.toString().contains("Municipality")){
                address.subLocality.toString().replace("Municipality", "kommune")

            }
            else{
                address.subLocality.toString().replace("municipality", "kommune")
            }
        }
        else if(address.subAdminArea!=null){
            return if (address.subAdminArea.toString().contains("Municipality")){
                address.subAdminArea.toString().replace("Municipality", "kommune")

            }else{
                address.subAdminArea.toString().replace("municipality", "kommune")
            }    }
        else if(address.locality!=null){
            return if (address.locality.toString().contains("Municipality")){
                address.locality.toString().replace("Municipality", "kommune")

            }else{
                address.locality.toString().replace("municipality", "kommune")
            }    }
        else if(address.adminArea!=null){

            return if (address.adminArea.toString().contains("Municipality")){
                address.adminArea.toString().replace("Municipality", "kommune")

            }else{
                address.adminArea.toString().replace("municipality", "kommune")
            }    }
        else{
            return address.countryName.toString()
        }
    }

}
