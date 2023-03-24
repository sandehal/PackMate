package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.BottomNavBar
import com.example.team14_turpakkeliste.ui.MapScreen
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme

@Composable
fun HomeScreen() {
    val image = painterResource(R.drawable.autumn_telt_1)
    Column(modifier = Modifier
        .fillMaxSize()
    ){
        Image(
            painter = image,
            contentDescription = "Telt"
        )
    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        BottomNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Team14TurPakkeListeTheme {
        HomeScreen()
    }
}