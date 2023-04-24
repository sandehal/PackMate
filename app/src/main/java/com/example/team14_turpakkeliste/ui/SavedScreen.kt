package com.example.team14_turpakkeliste


import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.EntityClass.AppDatabase
import com.example.team14_turpakkeliste.EntityClass.Pakkliste
import com.example.team14_turpakkeliste.ui.theme.Burgunder
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private lateinit var appDB : AppDatabase
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SavedScreen(navController: NavController) {
    val context = LocalContext.current
    appDB = AppDatabase.getDatabase(context)
    GlobalScope.launch(Dispatchers.IO) {
        appDB.UserDao().deleteAll()
        val user = Pakkliste(null,"haakon","zazamann")
        appDB.UserDao().insert(user)
        val all = appDB.UserDao().getAll()
        println(all)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Burgunder)) {
        Text( modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(20.dp),
            text = "Lagrede pakkelister:",
            fontSize = 30.sp
        )
    }
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .wrapContentWidth(Alignment.CenterHorizontally)
        .background(color = Burgunder)) {

    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        Text(modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(20.dp),
            text = "Klikk på Map for å lage en ny pakkeliste!",
            fontSize = 18.sp
        )
        BottomNavBar(navController)
    }

}

@Composable
fun SavedButton(navController: NavController){
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
        text = { Text("Hent siste pakkeliste") },
        onClick = {  navController.navigate("ListScreen")
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
        modifier = Modifier.fillMaxWidth()
    )
}




@Preview(showBackground = true)
@Composable
fun SavedPreview() {
    Team14TurPakkeListeTheme {
        SavedScreen(rememberNavController())
    }
}