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
            val alertList= mutableListOf<List<Alert>>()

            for (a in alerts) {
                val responseForAlert = source.getCurrentAlerts(a.link!!)
                val inputStreamForAlert : InputStream = responseForAlert.byteInputStream()
                alertList.add(XmlCurrentAlert().parse(inputStreamForAlert))
            }
            val forecast = source.getData()
        }
    }
}



