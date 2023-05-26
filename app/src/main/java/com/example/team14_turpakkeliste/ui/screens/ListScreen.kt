package com.example.team14_turpakkeliste.ui.screens

import com.example.team14_turpakkeliste.data.models.ForecastData
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.team14_turpakkeliste.EntityClass.AppDatabase
import com.example.team14_turpakkeliste.EntityClass.WeatherInfo
import com.example.team14_turpakkeliste.data.getweatherIcon
import com.example.team14_turpakkeliste.data.sortClothing
import com.example.team14_turpakkeliste.data.getWeather
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow
import com.example.team14_turpakkeliste.ui.theme.Yellow
import com.example.team14_turpakkeliste.ui.viewModel.TurViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Funksjonen setter opp en screen med  pakkeliste for antall dager brukeren har valgt.
 * */
@Composable
fun ListScreen(navController: NavController, viewModel: TurViewModel, forecastData: ForecastData){
    var farevarsel: String? = null
    if (viewModel.getAlertDataForArea()?.first != null){
        farevarsel = "icon_warning_${viewModel.getAlertDataForArea()?.first}_${viewModel.getAlertDataForArea()?.second}"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteYellow)
    ) {
        Text(
            text = "Hva trenger jeg?",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            for (i in 0..viewModel.numberOfDays) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ForestGreen,
                        contentColor = Color.White
                    ),
                    shape = RectangleShape,
                    //send med beskjed her om for å sortere klær som skal til clothingscreen henter ut in for om riktig dag
                    onClick = {
                        viewModel.chosenDay = i
                        viewModel.outerLayerList =
                            sortClothing("outer", getWeather(forecastData, i))
                        viewModel.innerLayerList =
                            sortClothing("inner", getWeather(forecastData, i))
                        viewModel.weatherInfo = getWeather(forecastData, i)
                        viewModel.weatherImg = getweatherIcon(forecastData, i)
                        viewModel.prevScreen = "ListScreen"
                        navController.navigate("ClothingScreen")
                        {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))

                ) {
                    Text(text = "Dag ${i + 1}", fontSize = 20.sp, fontWeight = Bold)
                    if (farevarsel != null) {
                        val image = getImg(desc = farevarsel)
                        Image(painter = image, contentDescription = "Hazard warning")
                    }
                }

            }
        }
        SaveButton(viewModel = viewModel, forecastData = forecastData )
        BottomNavBar(navController)

    }

}

private lateinit var appDB : AppDatabase

/**
 * Funksjonen lager en button som gir deg mulighet til å lagre pakkelisten ved å trykke på den.
 * */
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SaveButton(viewModel: TurViewModel, forecastData: ForecastData){
    var buttonState by remember { mutableStateOf(true) }
    val context: Context = LocalContext.current
    Button(
        enabled = buttonState,
        colors = ButtonDefaults.buttonColors(
            containerColor = Yellow,
            contentColor = Color.Black),
        shape = RectangleShape,
        onClick = {
            appDB = AppDatabase.getDatabase(context)
            GlobalScope.launch(Dispatchers.IO) {
                //gå gjennom variabelnavn her og tenk engelsk og riktig
                //Denne legger bare inn et objekt, og ikke flere for dagene
                //må da oppdatere databasen til å kunne holde på denne dataen!
                for(i in 0..viewModel.numberOfDays){
                    val dataForDay = when(i){
                        0 -> 2
                        1 -> 26
                        2 -> 40
                        else -> 0
                    }
                    val date = forecastData.properties.timeseries[dataForDay].time
                    val weather = getWeather(forecastData, i)
                    val img = getweatherIcon(forecastData, i)
                    val tempList = WeatherInfo(date, viewModel.location, i, weather.temp, weather.windspeed,weather.watermm, img)
                    appDB.UserDao().insert(tempList)
                }
            }
            buttonState = false
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = "Save list")
        Text("   Lagre listen", fontSize = 14.sp, fontWeight = Bold)
    }
}