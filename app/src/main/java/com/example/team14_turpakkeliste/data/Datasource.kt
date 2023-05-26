package com.example.team14_turpakkeliste.data
import com.example.team14_turpakkeliste.data.models.Alert
import com.example.team14_turpakkeliste.data.models.ForecastData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import java.io.InputStream

class Datasource {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { gson() }
        expectSuccess = true
    
    }

    /**Denne funksjonen henter værdata basert på breddegrad og lengdegrad
     */
    suspend fun getForecastData(newlat: Double, newlon: Double): ForecastData {
        val client = client.get("https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/compact?lat=${newlat}&lon=${newlon}"){
            headers{
                append("X-Gravitee-API-Key","f1163555-9b8d-49bd-b24f-49e1c756b215")
            }
        }
        return client.body()
    }

    //Henter MetAlerts-APIet som inneholder Alert API-er.
    /** Funksjonen bruker metAlerts-APIet til å hente varsler og returnerer en streng med flere varsler.
     * */
    private suspend fun getMetAlerts(): String {
        val client = client.get("https://gw-uio.intark.uh-it.no/in2000/weatherapi/metalerts/1.1?lang=no"){
            headers{
                append("X-Gravitee-API-Key","f1163555-9b8d-49bd-b24f-49e1c756b215")
            }
        }
        return client.bodyAsText()
    }



    /**
     * Denne funksjonen finner gjeldende alert med en tilhørende lenke og returnerer dataen til client
     * */
    private suspend fun getCurrentAlerts(link: String): String{
        val client = client.get(link){
            headers{
                append("X-Gravitee-API-Key","f1163555-9b8d-49bd-b24f-49e1c756b215")
            }
        }
        return client.bodyAsText()
    }

    //Gjør kall på getMetAlerts og getCurrentAlerts
    //Finner Alerts som er relevant for oss og returner dem i en liste.
    /**
     * Denne funksjonen utfører kall til getCurrentAlerts() for å hente varsler. Deretter filtrerer den ut varsler som er relevante og returnerer dem som en liste.
     */
    suspend fun getAllAlerts(): List<Alert>{
        val response = getMetAlerts()
        val inputStream : InputStream = response.byteInputStream()
        val alerts = XmlForMetAlerts().parse(inputStream)
        val alertListOfList= mutableListOf<List<Alert>>()
        val alertList = mutableListOf<Alert>()

        for (a in alerts) {
            val responseForAlert = getCurrentAlerts(a.link!!)
            val inputStreamForAlert : InputStream = responseForAlert.byteInputStream()
            alertListOfList.add(XmlCurrentAlert().parse(inputStreamForAlert))
        }
        //Kun Alerts med relevant informasjon for oss
        for (alertLists in alertListOfList){
            for (b in alertLists){
                if (b.domain == "land" && b.language == "no"){
                    alertList.add(b)
                }
            }
        }
        return alertList
    }


}