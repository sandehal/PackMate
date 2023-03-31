package com.example.team14_turpakkeliste.data
import ForecastData
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import java.io.IOException

class Datasource {

    //https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=60.12&lon=9.58
    //"https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${lat}lon=${lon}"

    private val apiUrl = "https://api.met.no/weatherapi/metalerts/1.1?lang=no"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }
    suspend fun getForecastData(newlat: Double, newlon: Double): ForecastData {
        val lat = newlat
        val lon = newlon
        return client.get("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${lat}&lon=${lon}").body()
    }
    suspend fun getMetAlerts(): String {
        return client.get(apiUrl).bodyAsText()
    }

    suspend fun getCurrentAlerts(link: String): String{
        return client.get(link).bodyAsText()
    }


}