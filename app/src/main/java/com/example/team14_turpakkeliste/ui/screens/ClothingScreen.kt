package com.example.team14_turpakkeliste.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.data.*
import com.example.team14_turpakkeliste.ui.theme.CardColor
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow
import com.example.team14_turpakkeliste.ui.viewModel.TurViewModel


@Composable
fun ClothingScreen(navController: NavController, viewModel: TurViewModel){
    BackHandler {
        navigate(navController, viewModel.prevScreen)
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .background(WhiteYellow),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = {
                    navigate(navController, viewModel.prevScreen)
                }
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")

            }

            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    navigate(navController, "InfoScreen")
                }
            ) {
                Icon(Icons.Outlined.Info, contentDescription = "Info")

            }
        }
        Text(text = "Ytterlag", fontSize = 18.sp, fontWeight = Bold)
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.outerLayerList) { outerlist ->
                val description = "Plagg: ${outerlist.material}${outerlist.type} \n" +
                        "Varme: ${outerlist.warmth}\n" +
                        "Vindtetthet: ${outerlist.windproof} \n" +
                        "Vanntetthet: ${outerlist.waterproof}"
                val image = outerlist.image
                NonExpandableCard(
                    description = description,
                    img = image)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Innerlag", fontSize = 18.sp, fontWeight = Bold)
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.innerLayerList) { innerList ->
                val description = "Plagg: ${innerList.material}${innerList.type} \n" +
                        "Varme: ${innerList.warmth}\n" +
                        "Vindtetthet: ${innerList.windproof} \n" +
                        "Vanntetthet: ${innerList.waterproof}"
                val image = innerList.image
                NonExpandableCard(
                    description = description,
                    img = image)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
    if(!viewModel.isOffline){
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            val alertInfo = viewModel.getAlertDataForArea()
            if(alertInfo != null){
                ExpandableCard(title = "Farevarsel",
                    description = alertInfo.third,
                    img = "icon_warning_${alertInfo.first}_${alertInfo.second}")
            }
            val info = viewModel.weatherInfo
            ExpandableCard(title = "Vis været",
                description = "Det er meldt ${info.temp} grader \n" +
                        "og vind på ${info.windspeed} m/s \n" +
                        "Du kan forvente ${String.format("%.1f", info.watermm)} mm nedbør i løpet av dagen",
                img = viewModel.weatherImg)
            BottomNavBar(navController)
        }
    } else{
        Column(modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            val info = viewModel.weatherInfo
            ExpandableCard(title = "Vis været",
                description = "Det er meldt ${info.temp} grader \n" +
                        "og vind på ${info.windspeed} m/s \n" +
                        "Du kan forvente ${String.format("%.1f", info.watermm)} mm nedbør i løpet av dagen",
                img = viewModel.weatherImg)
            BottomNavBar(navController)
        }
    }
}
@Composable
fun NonExpandableCard(
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp,
    img: String,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CardColor,
        ),
        modifier = Modifier
            .size(350.dp, 150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = 18.sp,
    titleFontWeight: FontWeight = Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    descriptionFontWeight: FontWeight = Bold,
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
        border = BorderStroke(1.dp, Color.Black),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
        //Klesplagg
        "cottonjacket"->painterResource(id = R.drawable.cottonjacket)
        "cottonpants"->painterResource(id = R.drawable.cottonpants)
        "downjacket"-> painterResource(id = R.drawable.downjacket)
        "goretexjacket"-> painterResource(id = R.drawable.goretexjacket)
        "goretexpants" -> painterResource(id = R.drawable.goretexpants)
        "lightgoretexjacket"-> painterResource(id = R.drawable.lightgoretexjacket)
        "lightgoretexpants" -> painterResource(id = R.drawable.lightgoretexpants)
        "primaloft" -> painterResource(id = R.drawable.primaloft)
        "ravgenser" -> painterResource(id = R.drawable.warmsweater)
        "ravbukse" -> painterResource(id = R.drawable.warmpants)
        "sommerull" -> painterResource(id = R.drawable.sommerull)
        "windjacket" -> painterResource(id = R.drawable.windjacket)
        "flexshorts" -> painterResource(id = R.drawable.flexshorts)
        "flexpants" -> painterResource(id = R.drawable.flexpants)
        "thermalfleece"-> painterResource(id = R.drawable.thermalfleece)
        "thinfleece" -> painterResource(id = R.drawable.thinfleece)
        "heavywool" -> painterResource(id = R.drawable.heavywool)
        "trekkingpants" -> painterResource(id = R.drawable.trekkingpants)
        "heavypants" -> painterResource(id = R.drawable.heavypants)
        "lightwoolsweater" -> painterResource(id = R.drawable.lightwoolsweater)
        "lightwoolpants" -> painterResource(id = R.drawable.lightwoolpants)
        "expeditionsweater" -> painterResource(id = R.drawable.expeditionsweater)
        "expeditionpants" -> painterResource(id = R.drawable.expeditionpants)
        "thermosweater" -> painterResource(id = R.drawable.thermosweater)
        "thermopants" -> painterResource(id = R.drawable.thermopants)
        "warmsweater" -> painterResource(id = R.drawable.warmsweater)
        "warmpants" -> painterResource(id = R.drawable.warmpants)
        "thinnestfleece" -> painterResource(id = R.drawable.thinnestfleece)
        "alphajacket" -> painterResource(id = R.drawable.alphajacket)
        "wooljacket" -> painterResource(id = R.drawable.wooljacket)
        "mediumfleece" -> painterResource(id = R.drawable.mediumfleece)
        "mediumwoolsweater" -> painterResource(id = R.drawable.ravgenser)
        "mediumwoolpants" -> painterResource(id = R.drawable.ravbukse)
        "shelldownparka" -> painterResource(id = R.drawable.goretextdownparka)
        "mediumdown" -> painterResource(id = R.drawable.mediumwarmdown)
        "ullboxer" -> painterResource(id = R.drawable.ullboxer)

        //farevarsel
        "icon_warning_flood_yellow" -> painterResource(id = R.drawable.icon_warning_flood_yellow)
        "icon_warning_forest_yellow" -> painterResource(id = R.drawable.icon_warning_forestfire_yellow)
        "icon_warning_forest_orange"-> painterResource(id = R.drawable.icon_warning_forestfire_orange)
        "icon_warning_forest_red" -> painterResource(id = R.drawable.icon_warning_forestfire_red)
        "icon_warning_generic_orange" -> painterResource(id = R.drawable.icon_warning_forestfire_yellow)
        "icon_warning_generic_red" -> painterResource(id = R.drawable.icon_warning_generic_red)
        "icon_warning_generic_yellow" -> painterResource(id = R.drawable.icon_warning_generic_yellow)
        "icon_warning_ice_orange" -> painterResource(id = R.drawable.icon_warning_ice_orange)
        "icon_warning_ice_red" -> painterResource(id = R.drawable.icon_warning_ice_red)
        "icon_warning_ice_yellow" -> painterResource(id = R.drawable.icon_warning_ice_yellow)
        "icon_warning_landslide_orange" -> painterResource(id = R.drawable.icon_warning_landslide_orange)
        "icon_warning_landslide_red"-> painterResource(id = R.drawable.icon_warning_landslide_red)
        "icon_warning_landslide_yellow" -> painterResource(id = R.drawable.icon_warning_landslide_yellow)
        "icon_warning_lightning_orange"  -> painterResource(id = R.drawable.icon_warning_lightning_orange)
        "icon_warning_lightning_red"  -> painterResource(id = R.drawable.icon_warning_lightning_red)
        "icon_warning_lightning_yellow" -> painterResource(id = R.drawable.icon_warning_lightning_yellow)
        "icon_warning_polarlow_orange"  -> painterResource(id = R.drawable.icon_warning_polarlow_orange)
        "icon_warning_polarlow_red"  -> painterResource(id = R.drawable.icon_warning_polarlow_red)
        "icon_warning_polarlow_yellow" -> painterResource(id = R.drawable.icon_warning_polarlow_yellow)
        "icon_warning_rainflood_orange" -> painterResource(id = R.drawable.icon_warning_rainflood_orange)
        "icon_warning_raindflood_red" -> painterResource(id = R.drawable.icon_warning_rainflood_red)
        "icon_warning_rainflood_yellow" -> painterResource(id = R.drawable.icon_warning_rainflood_yellow)
        "icon_warning_rain_orange" -> painterResource(id = R.drawable.icon_warning_rain_orange)
        "icon_warning_rain_red" -> painterResource(id = R.drawable.icon_warning_rain_red)
        "icon_warning_rain_yellow" -> painterResource(id = R.drawable.icon_warning_rain_yellow)
        "icon_warning_snow_orange" -> painterResource(id = R.drawable.icon_warning_snow_orange)
        "icon_warning_snow_red" -> painterResource(id = R.drawable.icon_warning_snow_red)
        "icon_warning_snow_yellow" -> painterResource(id = R.drawable.icon_warning_snow_yellow)
        "icon_warning_stormsurge_orange" -> painterResource(id = R.drawable.icon_warning_stormsurge_orange)
        "icon_warning_stormsurge_red"-> painterResource(id = R.drawable.icon_warning_stormsurge_red)
        "icon_warning_stormsurge_yellow" -> painterResource(id = R.drawable.icon_warning_stormsurge_yellow)
        "icon_warning_wind_orange" -> painterResource(id = R.drawable.icon_warning_wind_orange)
        "icon_warning_wind_red" -> painterResource(id = R.drawable.icon_warning_wind_red)
        "icon_warning_wind_yellow" -> painterResource(id = R.drawable.icon_warning_wind_yellow)
        "icon_warning_avalanches_orange" -> painterResource(id = R.drawable.icon_warning_avalanches_orange)
        "icon_warning_avalanches_red" -> painterResource(id = R.drawable.icon_warning_avalanches_red)
        "icon_warning_avalanches_yellow" -> painterResource(id = R.drawable.icon_warning_avalanches_yellow)
        "icon_warning_drivingconditions_orange" -> painterResource(id = R.drawable.icon_warning_drivingconditions_orange)
        "icon_warning_drivingconditions_red" -> painterResource(id = R.drawable.icon_warning_drivingconditions_red)
        "icon_warning_drivingconditions_yellow" -> painterResource(id = R.drawable.icon_warning_drivingconditions_yellow)
        "icon_warning_extreme_red" -> painterResource(id = R.drawable.icon_warning_extreme)
        "icon-warning-flood-orange" -> painterResource(id = R.drawable.icon_warning_flood_orange)


        //værdata
        "clearsky_day" -> painterResource(id = R.drawable.clearsky_day)
        "clearsky_night" -> painterResource(id = R.drawable.clearsky_night)
        "cloudy" -> painterResource(id = R.drawable.cloudy)
        "fair_day" -> painterResource(id = R.drawable.fair_day)
        "fair_night" -> painterResource(id = R.drawable.fair_night)
        "fair_polartwilight" -> painterResource(id = R.drawable.fair_polartwilight)
        "fog" -> painterResource(id = R.drawable.fog)
        "heavyrain" -> painterResource(id = R.drawable.heavyrain)
        "heavyrainandthunder" -> painterResource(id = R.drawable.heavyrainandthunder)
        "heavyrainshowers_day" -> painterResource(id = R.drawable.heavyrainshowers_day)
        "heavyrainshowers_night" -> painterResource(id = R.drawable.heavyrainshowers_night)
        "heavyrainshowers_polartwilight" -> painterResource(id = R.drawable.heavyrainshowers_polartwilight)
        "heavyrainshowersandthunder_day" -> painterResource(id = R.drawable.heavyrainshowersandthunder_day)
        "heavyrainshowersandthunder_night" -> painterResource(id = R.drawable.heavyrainshowersandthunder_night)
        "heavyrainshowersandthunder_polartwilight" -> painterResource(id = R.drawable.heavyrainshowersandthunder_polartwilight)
        "heavysleet" -> painterResource(id = R.drawable.heavysleet)
        "heavysleetandthunder" -> painterResource(id = R.drawable.heavysleetandthunder)
        "heavysleetshowers_day"-> painterResource(id = R.drawable.heavysleetshowers_day)
        "heavysleetshowers_night" -> painterResource(id = R.drawable.heavysleetshowers_night)
        "heavysleetshowers_polartwilight" -> painterResource(id = R.drawable.heavysleetshowers_polartwilight)
        "heavysleetshowersandthunder_day" -> painterResource(id = R.drawable.heavysleetshowersandthunder_day)
        "heavysleetshowersandthunder_night" -> painterResource(id = R.drawable.heavysleetshowersandthunder_night)
        "heavysleetshowersandthunder_polartwilight" -> painterResource(id = R.drawable.heavysleetshowersandthunder_polartwilight)
        "heavysnow" -> painterResource(id = R.drawable.heavysnow)
        "heavysnowandthunder" -> painterResource(id = R.drawable.heavysnowandthunder)
        "heavysnowshowers_day"-> painterResource(id = R.drawable.heavysnowshowers_day)
        "heavysnowshowers_night" -> painterResource(id = R.drawable.heavysnowshowers_night)
        "heavysnowshowers_polartwilight" -> painterResource(id = R.drawable.heavysnowshowers_polartwilight)
        "heavysnowshowersandthunder_day" -> painterResource(id = R.drawable.heavysnowshowersandthunder_day)
        "heavysnowshowersandthunder_night" -> painterResource(id = R.drawable.heavysnowshowersandthunder_night)
        "heavysnowshowersandthunder_polartwilight" -> painterResource(id = R.drawable.heavysnowshowersandthunder_polartwilight)
        "lightrain" -> painterResource(id = R.drawable.lightrain)
        "lightrainandthunder" -> painterResource(id = R.drawable.lightrainandthunder)
        "lightrainshowers_day" -> painterResource(id = R.drawable.lightrainshowers_day)
        "lightrainshowers_night" -> painterResource(id = R.drawable.lightrainshowers_night)
        "lightrainshowers_polartwilight" -> painterResource(id = R.drawable.lightrainshowers_polartwilight)
        "lightrainshowersandthunder_day" -> painterResource(id = R.drawable.lightrainshowersandthunder_day)
        "lightrainshowersandthunder_night" -> painterResource(id = R.drawable.lightrainshowersandthunder_night)
        "lightrainshowersandthunder-polartwilight" -> painterResource(id = R.drawable.lightrainshowersandthunder_polartwilight)
        "lightsleet" -> painterResource(id = R.drawable.lightsleet)
        "lightsleetandthunder" -> painterResource(id = R.drawable.lightsleetandthunder)
        "lightsleetshowers_day"-> painterResource(id = R.drawable.lightsleetshowers_day)
        "lightsleetshowers_night" -> painterResource(id = R.drawable.lightsleetshowers_night)
        "lightsleetshowers_polartwilight" -> painterResource(id = R.drawable.lightsleetshowers_polartwilight)
        "lightssleetshowersandthunder_day" -> painterResource(id = R.drawable.lightssleetshowersandthunder_day)
        "lightssleetshowersandthunder_night" -> painterResource(id = R.drawable.lightssleetshowersandthunder_night)
        "lightssleetshowersandthunder_polartwilight" -> painterResource(id = R.drawable.lightssleetshowersandthunder_polartwilight)
        "lightsnow" -> painterResource(id = R.drawable.lightsnow)
        "lightsnowandthunder" -> painterResource(id = R.drawable.lightsnowandthunder)
        "lightsnowshowers_day"-> painterResource(id = R.drawable.lightsnowshowers_day)
        "lightsnowshowers_night" -> painterResource(id = R.drawable.lightsnowshowers_night)
        "lightsnowshowers_polartwilight" -> painterResource(id = R.drawable.lightsnowshowers_polartwilight)
        "lightssnowshowersandthunder_day" -> painterResource(id = R.drawable.lightssnowshowersandthunder_day)
        "lightsnowshowersandthunder_night" -> painterResource(id = R.drawable.lightssnowshowersandthunder_night)
        "lightsnowshowersandthunder_polartwilight" -> painterResource(id = R.drawable.lightssnowshowersandthunder_polartwilight)
        "partlycloudy_day" -> painterResource(id = R.drawable.partlycloudy_day)
        "partlycloudy_night" -> painterResource(id = R.drawable.partlycloudy_night)
        "partlycloudy_polartwilight" -> painterResource(id = R.drawable.partlycloudy_polartwilight)
        "rain" -> painterResource(id = R.drawable.rain)
        "rainandthunder" -> painterResource(id = R.drawable.rainandthunder)
        "rainshowers_day" -> painterResource(id = R.drawable.rainshowers_day)
        "rainshowers_night" -> painterResource(id = R.drawable.rainshowers_night)
        "rainshowers_polartwilight" -> painterResource(id = R.drawable.rainshowers_polartwilight)
        "rainshowersandthunder_day" -> painterResource(id = R.drawable.rainshowersandthunder_day)
        "rainshowersandthunder_night" -> painterResource(id = R.drawable.rainshowersandthunder_night)
        "rainshowersandthunder-polartwilight" -> painterResource(id = R.drawable.rainshowersandthunder_polartwilight)
        "sleet" -> painterResource(id = R.drawable.sleet)
        "sleetandthunder" -> painterResource(id = R.drawable.sleetandthunder)
        "sleetshowers_day"-> painterResource(id = R.drawable.sleetshowers_day)
        "sleetshowers_night" -> painterResource(id = R.drawable.sleetshowers_night)
        "sleetshowers_polartwilight" -> painterResource(id = R.drawable.sleetshowers_polartwilight)
        "sleetshowersandthunder_day" -> painterResource(id = R.drawable.sleetshowersandthunder_day)
        "sleetshowersandthunder_night" -> painterResource(id = R.drawable.sleetshowersandthunder_night)
        "sleetshowersandthunder_polartwilight" -> painterResource(id = R.drawable.sleetshowersandthunder_polartwilight)
        "snow" -> painterResource(id = R.drawable.snow)
        "snowandthunder" -> painterResource(id = R.drawable.snowandthunder)
        "snowshowers_day"-> painterResource(id = R.drawable.snowshowers_day)
        "snowshowers_night" -> painterResource(id = R.drawable.snowshowers_night)
        "snowshowers_polartwilight" -> painterResource(id = R.drawable.snowshowers_polartwilight)
        "snowshowersandthunder_day" -> painterResource(id = R.drawable.snowshowersandthunder_day)
        "snowshowersandthunder_night" -> painterResource(id = R.drawable.snowshowersandthunder_night)
        "snowshowersandthunder_polartwilight" -> painterResource(id = R.drawable.snowshowersandthunder_polartwilight)
        else -> {
            painterResource(id = R.drawable.ic_launcher_foreground)
        }
    }
    return painter
}


