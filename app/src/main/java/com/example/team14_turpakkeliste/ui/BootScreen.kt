package com.example.team14_turpakkeliste.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.team14_turpakkeliste.ClothingScreen
import com.example.team14_turpakkeliste.SavedScreen
import com.example.team14_turpakkeliste.ui.theme.Burgunder
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.example.team14_turpakkeliste.ui.theme.Orange

@Composable
fun BootScreen(context: Context){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "LoadingScreen") {
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.MapScreen.route) { MapScreen(navController) }
        composable(Screen.SavedScreen.route) { SavedScreen(navController) }
        composable(Screen.ClothingScreen.route) { ClothingScreen(context, navController) }
        composable(Screen.LoadingScreen.route) { LoadingScreen(navController) }
    }

}
sealed class Screen(val route: String, val icon: ImageVector, val description : String) {
    object MapScreen : Screen("MapScreen", Icons.Default.Search, "Map")
    object HomeScreen : Screen("HomeScreen", Icons.Default.Home, "Home")
    object SavedScreen : Screen("SavedScreen", Icons.Default.Star, "Saved")
    object ClothingScreen : Screen("ClothingScreen", Icons.Default.Favorite, "Clothing")
    object LoadingScreen : Screen("LoadingScreen", Icons.Default.Refresh, "Loading")
}

@Composable
fun BottomNavBar(navController: NavController){
    val items = listOf(
        Screen.MapScreen,
        Screen.HomeScreen,
        Screen.SavedScreen,
    )


    NavigationBar(
        containerColor = Orange,
        modifier = Modifier
            .border(BorderStroke(2.dp, Color.Black))
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