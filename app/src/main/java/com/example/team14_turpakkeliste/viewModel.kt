package com.example.team14_turpakkeliste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class viewModel(): ViewModel() {


    init {
        viewModelScope.launch {
            val aa = XMLtest()
            println(aa.getData())
        }
    }
}