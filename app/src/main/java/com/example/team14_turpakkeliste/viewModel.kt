package com.example.team14_turpakkeliste


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.data.*
import kotlinx.coroutines.launch
import java.io.InputStream

class viewModel(): ViewModel() {

    private val source: Datasource = Datasource()


    init {
        viewModelScope.launch {

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
            val forecast = source.getData()
        }
    }
}



