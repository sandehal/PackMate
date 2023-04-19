package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.BottomNavBar
import com.example.team14_turpakkeliste.SaveButton

@Composable
fun ListScreen(navController: NavController){

    Column() {
        Text(
            text = "Hva trenger jeg?"
        )

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom) {
        SaveButton()
        BottomNavBar(navController)
    }
}