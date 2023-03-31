package com.example.team14_turpakkeliste

import ForecastData
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.data.*
import com.example.team14_turpakkeliste.ui.TurViewModel


@Composable
fun ClothingScreen(navController: NavController, forecastData: ForecastData, alerts: List<Alert>,viewModel: TurViewModel){
    val recommendedList = sortClothing(forecastData)
    for(alert in alerts){
        if(pinpointLocation(viewModel.currentLatitude,viewModel.currentLongitude,alert.areaPolygon!!)){
            println(alert.headline)
            println(alert.severity)
        }
    }

    Column() {
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            items(recommendedList) { recommendedList ->
                val title = "${recommendedList.material}${recommendedList.type}"
                val description =
                    "Varme: ${recommendedList.warmth}\nVindtetthet: ${recommendedList.windproof} \nVanntetthet: ${recommendedList.waterproof}"
                val image = recommendedList.image
                ExpandableCard(title = title, description = description, img = image)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        val descriptionWeather = getWeather(forecastData)
        ExpandableCard(title = "Vis Været", description = descriptionWeather, img = "hei")
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            BottomNavBar(navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp,
    img: String
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),

        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                val image = getImg(desc = img)
                Image(painter = image, contentDescription = "picture of clothing-piece")
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun getImg(desc: String): Painter{
    val painter: Painter = when(desc){
        "SoftJO"->painterResource(id = R.drawable.softshelljakke)
        "SoftPO"->painterResource(id = R.drawable.softshellbukse)
        "DownJO"-> painterResource(id = R.drawable.dunjakke)
        "ShellJO"-> painterResource(id = R.drawable.skalljakke)
        //"ShellPO" -> painterResource(id = R.drawable.skallbukse)
        else -> {
            painterResource(id = R.drawable.tursekk_1)
        }
    }
    return painter
}