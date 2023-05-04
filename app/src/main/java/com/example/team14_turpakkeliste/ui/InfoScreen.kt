package com.example.team14_turpakkeliste.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow

@Composable
fun InfoScreen(navController: NavController){
    BackHandler {
        navigate(navController, "ClothingScreen")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteYellow),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //test
        Text(text ="Varmeverdier\n"+
            "6 = -30.0 -> -20.0 grader\n" +
            "5 = -19.9 -> -10.0 grader\n"+
            "4 = -9.9 -> -0.1 grader\n"+
            "3 = 0.0 -> 9.9 grader\n"+
            "2 = 10.0 -> 15.9 grader\n"+
            "1 = 16.0 -> 30.0 grader\n")
        Text(text ="Vindverdier\n"+
                "1 = 0.0 -> 2.5 m/s\n" +
                "2 = 2.6 -> 5.0 m/s\n"+
                "3 = 5.1 -> 7.5 m/s\n"+
                "4 = 7.6 -> 10.0 m/s\n"+
                "5 = 10.0 -> 17.5 m/s\n"+
                "6 = 17.6 -> 32.7 m/s\n")
        Text(text ="Nedbørsverdierverdier\n"+
                "1 = 0.0 -> 0.1 mm\n" +
                "2 = 0.2 -> 0.3 mm\n"+
                "3 = 0.4 -> 0.8 mm\n"+
                "utilstrekkelig klesdata for nivå 4"+
                "5 = 0.9 -> 1.4 mm\n"+
                "6 = 1.5 -> 50.0 mm\n")
    }
}