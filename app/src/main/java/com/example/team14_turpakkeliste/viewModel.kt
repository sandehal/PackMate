package com.example.team14_turpakkeliste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.data.Datasource
import kotlinx.coroutines.launch

class viewModel(): ViewModel() {


    init {
        viewModelScope.launch {

            val source = Datasource()
            val forecast = source.getData()

            println(forecast.properties  )
        }
    }
}