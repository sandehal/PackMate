package com.example.team14_turpakkeliste.ui

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.ClothingScreen
import com.example.team14_turpakkeliste.SavedScreen
import com.example.team14_turpakkeliste.ui.theme.ForestGreen

@Composable
fun BootScreen(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.MapScreen.route) { MapScreen(navController) }
        composable(Screen.SavedScreen.route) { SavedScreen(navController) }
        composable(Screen.ClothingScreen.route) { ClothingScreen(context = LocalContext.current, navController) }
    }

}
sealed class Screen(val route: String, val icon: ImageVector, val description : String) {
    object MapScreen : Screen("MapScreen", Icons.Default.Search, "Map")
    object HomeScreen : Screen("HomeScreen", Icons.Default.Home, "Home")
    object SavedScreen : Screen("SavedScreen", Icons.Default.Star, "Saved")
    object ClothingScreen : Screen("ClothingScreen", Icons.Default.Favorite, "Clothing")
}

@Composable
fun BottomNavBar(navController: NavController){
    val items = listOf(
        Screen.MapScreen,
        Screen.HomeScreen,
        Screen.SavedScreen,
    )


    NavigationBar(
        containerColor = ForestGreen
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.route) },
                label = { Text(item.description) },
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

@Composable
fun makeListButton(navController: NavController){
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
        text = { Text("Make the list!") },
        onClick = {  navController.navigate("ClothingScreen")
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