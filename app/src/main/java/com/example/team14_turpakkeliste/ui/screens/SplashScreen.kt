package com.example.team14_turpakkeliste.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow


/**
 * Funksjonen initierer en splash animation av logoet til appen.
 * */
@Composable
fun SplashScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(WhiteYellow)

    ){

        val scale = remember {
            androidx.compose.animation.core.Animatable(0.0f)
        }

        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(700, easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
            )


        }

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "SplashScreen",
            alignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
                .scale(scale.value)
        )
        Text(
            text = "PackMate",
            textAlign = TextAlign.Center,
            fontSize = 44.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Team14TurPakkeListeTheme {
        SplashScreen()
    }
}