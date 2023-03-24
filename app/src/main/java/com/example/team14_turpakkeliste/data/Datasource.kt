package com.example.team14_turpakkeliste.data
import ForecastData
import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*

class Datasource {

    //https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=60.12&lon=9.58
    var lat = 60.12
    var lon = 9.58
    private val apiUrl1 =
        "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${lat}&lon=${lon}"
    private val apiUrl = "https://api.met.no/weatherapi/metalerts/1.1?lang=no"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getData(): ForecastData {
        val forecast: ForecastData = client.get(apiUrl).body()
        return forecast
    }
    suspend fun getMetAlerts(): String {
        return client.get(apiUrl).bodyAsText()
    }
}