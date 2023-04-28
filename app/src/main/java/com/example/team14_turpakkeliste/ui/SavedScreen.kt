package com.example.team14_turpakkeliste.ui


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.EntityClass.AppDatabase
import com.example.team14_turpakkeliste.EntityClass.WeatherInfo
import com.example.team14_turpakkeliste.TurViewModel
import com.example.team14_turpakkeliste.data.ForecastData
import com.example.team14_turpakkeliste.data.WeatherValues
import com.example.team14_turpakkeliste.data.getWeather
import com.example.team14_turpakkeliste.data.getweatherIcon
import com.example.team14_turpakkeliste.data.sortClothing
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow
import com.example.team14_turpakkeliste.ui.theme.Yellow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SavedScreen(navController: NavController, error: String?, viewModel: TurViewModel) {
    val context = LocalContext.current
    val appDB = AppDatabase.getDatabase(context)
    val saved = appDB.UserDao().getAll()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(
            snackbarHostState,
        ) },
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteYellow),

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteYellow)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(20.dp)
        ) {
            Text( modifier = Modifier
                .fillMaxWidth()
                .background(WhiteYellow)
                .wrapContentWidth(Alignment.CenterHorizontally),
                text = "Lagrede pakkelister:",
                fontSize = 30.sp
            )
            DeleteButton(navController)

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(WhiteYellow)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(bottom = 50.dp)) {
                for (s in saved){
                    item{
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            containerColor = ForestGreen,
                            contentColor = Color.White,
                            content = {Text("${s.location},  ${s.date}C")},
                            onClick = {
                                viewModel.outerLayerList = sortClothing( "outer",  WeatherValues(s.temperature!!, s.windspeed!!, s.watermilimeter))
                                viewModel.innerLayerList = sortClothing( "inner",  WeatherValues(s.temperature!!, s.windspeed!!, s.watermilimeter))
                                viewModel.weatherInfo = WeatherValues(s.temperature!!, s.windspeed!!, s.watermilimeter)
                                viewModel.weatherImg = s.image.toString()
                                navController.navigate("ClothingScreen")
                            {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                            }
                        )
                    }
                }

            }
            if (error != null){
                scope.launch {
                    snackbarHostState.showSnackbar(
                        error
                    )
                }
            }
        }
        Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ){
            if (saved.isEmpty()){
                Text(modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(20.dp),
                        text = "Klikk på Map for å lage en ny pakkeliste!",
                        fontSize = 18.sp
                    )
                }
                BottomNavBar(navController)
            }
        }
    }
@Preview(showBackground = true)
@Composable
fun SavedPreview() {
    Team14TurPakkeListeTheme {
        SavedScreen(rememberNavController(), null, viewModel = TurViewModel())
    }
}

private lateinit var appDB : AppDatabase
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DeleteButton(navController: NavController){
    val context: Context = LocalContext.current
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(139,0,0),
            contentColor = Color.Black),
        onClick = {
            appDB = AppDatabase.getDatabase(context)
            GlobalScope.launch(Dispatchers.IO) {
                    appDB.UserDao().deleteAll()
            }
            navController.navigate("SavedScreen")
                  },
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(Icons.Filled.Delete, contentDescription = "Slett alle", tint = Color.White)
        Text("Slett alle", color = Color.White)
    }
}