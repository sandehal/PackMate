package com.example.team14_turpakkeliste.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.team14_turpakkeliste.BottomNavBar
import com.example.team14_turpakkeliste.EntityClass.AppDatabase
import com.example.team14_turpakkeliste.EntityClass.Pakkliste
import com.example.team14_turpakkeliste.MainActivity
import com.example.team14_turpakkeliste.databinding.ActivityMainBinding
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun ListScreen(navController: NavController, viewModel: TurViewModel){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hva trenger jeg?",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        for(i in 0..viewModel.numberOfDays){ExtendedFloatingActionButton(

            containerColor = ForestGreen,
            contentColor = Color.White,
            icon = { Icon(Icons.Filled.Email, contentDescription = null) },
            text = { Text("Dag ${i+1}") },
                //send med beskjed her om for å sortere klær som skal til clothingscreen henter ut infor om riktig dag
                onClick = { viewModel.chosenDay = i; navController.navigate("ClothingScreen")
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
                },
                modifier = Modifier
                .fillMaxWidth()
                    .height(200.dp)
                    .padding(20.dp)
            )
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom) {
        SaveButton()
        BottomNavBar(navController)
    }
}

private lateinit var appDB : AppDatabase
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SaveButton(){
    val context: Context = LocalContext.current
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Save list") },
        text = { Text("Save list") },
        onClick = {
            appDB = AppDatabase.getDatabase(context)
            GlobalScope.launch(Dispatchers.IO) {
                val user = Pakkliste(null,"haakon","zazamann")
                appDB.UserDao().insert(user)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}