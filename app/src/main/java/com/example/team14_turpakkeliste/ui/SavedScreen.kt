package com.example.team14_turpakkeliste


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.ui.theme.Burgunder
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme



@Composable
fun SavedScreen(navController: NavController) {

    Column(
    ) {

    }
    Column(modifier = Modifier
        .fillMaxSize().background(color = Burgunder),
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