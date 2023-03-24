package com.example.team14_turpakkeliste

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.data.*

import com.example.team14_turpakkeliste.ui.HomeScreen
import com.example.team14_turpakkeliste.ui.MapScreen
import com.example.team14_turpakkeliste.ui.theme.ForestGreen

import kotlinx.coroutines.launch
import java.io.InputStream

class viewModel(): ViewModel() {

    private val source: Datasource = Datasource()


    init {
        viewModelScope.launch {

            val response = source.getMetAlerts()
            val inputStream : InputStream = response.byteInputStream()
            val alerts = XmlForMetAlerts().parse(inputStream)
            val alertList= mutableListOf<List<Alert>>()

            for (a in alerts) {
                val responseForAlert = source.getCurrentAlerts(a.link!!)
                val inputStreamForAlert : InputStream = responseForAlert.byteInputStream()
                alertList.add(XmlCurrentAlert().parse(inputStreamForAlert))
            }
            val forecast = source.getData()
        }
    }
}

@Composable
fun bootScreen(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.MapScreen.route) { MapScreen(navController) }
        composable(Screen.SavedScreen.route) { SavedScreen(navController) }

    }

}
sealed class Screen(val route: String,  val icon: ImageVector) {
    object HomeScreen : Screen("HomeScreen", Icons.Default.Home)
    object MapScreen : Screen("MapScreen", Icons.Default.Search)
    object SavedScreen : Screen("SavedScreen", Icons.Default.Star)
}

@Composable
fun BottomNavBar(navController: NavController){
    val items = listOf(
        Screen.HomeScreen,
        Screen.MapScreen,
        Screen.SavedScreen
    )

    NavigationBar(
        containerColor = ForestGreen
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.route) },
                label = { Text(item.route) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                navController.navigate(item.route)
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

