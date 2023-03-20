package com.example.team14_turpakkeliste

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import java.io.InputStream

class DataSource {
    private val apiUrl = "https://api.met.no/weatherapi/metalerts/1.1?lang=no"
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun getMetAlerts(): String {
        return client.get(apiUrl).bodyAsText()
    }
}