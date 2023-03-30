package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.BottomNavBar
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.MapScreen
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme

@Composable
fun HomeScreen(navController: NavController) {
    val image = painterResource(R.drawable.autumn_telt_1)
    Column(modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = image,
            contentDescription = "Telt",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)

        )
        BottomNavBar(navController)
    }

}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Team14TurPakkeListeTheme {
        HomeScreen(rememberNavController())
    }
}