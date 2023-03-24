package com.example.team14_turpakkeliste


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.ui.theme.Burgunder
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme



@Composable
fun SavedScreen(navController: NavController) {

    Column() {

    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        BottomNavBar(navController)
    }

}




@Preview(showBackground = true)
@Composable
fun SavedPreview() {
    Team14TurPakkeListeTheme {
        SavedScreen(rememberNavController())
    }
}