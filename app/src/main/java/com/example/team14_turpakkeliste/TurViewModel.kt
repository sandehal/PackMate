package com.example.team14_turpakkeliste.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.data.*
import com.example.team14_turpakkeliste.ui.TurpakklisteUiState
import io.ktor.client.plugins.*
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.io.InputStream

class TurViewModel(): ViewModel() {

    var turUiState: TurpakklisteUiState by mutableStateOf(TurpakklisteUiState.Booting)
        private set

    private val source: Datasource = Datasource()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            turUiState = try {
                val response = source.getMetAlerts()
                val inputStream : InputStream = response.byteInputStream()
                val alerts = XmlForMetAlerts().parse(inputStream)
                val alertListOfList= mutableListOf<List<Alert>>()
                val alertList = mutableListOf<Alert>()

                for (a in alerts) {
                    val responseForAlert = source.getCurrentAlerts(a.link!!)
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
                println(alertList.size)
                val forecast = source.getForecastData()

                TurpakklisteUiState.Success(alertList, forecast)
            } catch (ex: ResponseException) {
                TurpakklisteUiState.Error
            } catch (ex: SerializationException) {
                TurpakklisteUiState.Error
            }catch (ex: Exception) {
                TurpakklisteUiState.Error
            }
        }
    }
}
