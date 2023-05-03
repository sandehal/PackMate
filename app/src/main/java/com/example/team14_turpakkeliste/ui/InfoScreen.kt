package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow

@Composable
fun InfoScreen(){
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
                "6 = -30.0 -> -20.0 m/s\n" +
                "5 = -19.9 -> -10.0 m/s\n"+
                "4 = -9.9 -> -0.1 m/s\n"+
                "3 = 0.0 -> 9.9 m/s\n"+
                "2 = 10.0 -> 15.9 m/s\n"+
                "1 = 16.0 -> 30.0 m/s\n")
        Text(text ="Nedbørsverdierverdier\n"+
                "6 = -30.0 -> -20.0 grader\n" +
                "5 = -19.9 -> -10.0 grader\n"+
                "4 = -9.9 -> -0.1 grader\n"+
                "3 = 0.0 -> 9.9 grader\n"+
                "2 = 10.0 -> 15.9 grader\n"+
                "1 = 16.0 -> 30.0 grader\n")
    }
}