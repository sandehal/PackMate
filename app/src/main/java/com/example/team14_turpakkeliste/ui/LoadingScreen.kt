package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.BuildConfig
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme

@Composable
fun LoadingScreen(navController: NavController) {
    Box(
        Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.tursekk_1),
            contentDescription = "SplashScreen",
            alignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)

        )

        Text(
            text = "Version - ${BuildConfig.VERSION_NAME}",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Team14TurPakkeListeTheme {
        LoadingScreen(navController = rememberNavController())
    }
}