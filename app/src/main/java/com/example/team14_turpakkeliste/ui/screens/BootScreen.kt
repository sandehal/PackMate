package com.example.team14_turpakkeliste.ui.screens

import com.example.team14_turpakkeliste.data.models.ForecastData
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.viewModel.TurViewModel
import com.example.team14_turpakkeliste.ui.viewModel.TurpakklisteUiState
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow


/**
 * Basert på UI-staten sender funksjonen brukeren til riktig skjerm
 * */
@Composable
fun SetStateScreen(navController: NavHostController,viewModel: TurViewModel = viewModel()){
    when(val state = viewModel.turUiState){
        is TurpakklisteUiState.Booting -> SplashScreen()
        is TurpakklisteUiState.Error -> ErrorScreen(viewModel)
        is TurpakklisteUiState.OfflineMode -> BootScreen(navController,  null, viewModel)
        is TurpakklisteUiState.Loading -> LoadingScreen()
        is TurpakklisteUiState.Success -> BootScreen(navController,state.forecastData, viewModel)
    }
}

/**
 * Funksjonen holder styr på de ulike skjermene og setter startdestinasjon.
 * */
@Composable
fun BootScreen(navController: NavHostController, forecastData: ForecastData?, viewModel: TurViewModel){
    NavHost(navController = navController, startDestination = "SavedScreen") {
        composable(Screen.ListScreen.route) { ListScreen(navController, viewModel, forecastData!!) }
        composable(Screen.MapScreen.route) { MapsComposeScreen(navController,viewModel) }
        composable(Screen.SavedScreen.route) { SavedScreen(navController, viewModel) }
        composable(Screen.LoadingScreen.route) { LoadingScreen() }
        composable(Screen.ClothingScreen.route) { ClothingScreen(navController,viewModel) }
        composable(Screen.InfoScreen.route) { InfoScreen(navController) }
    }
}

/**
 * Funksjonen initialiserer LoadingScreen som er en innlastningsskjerm*/
@Composable
fun LoadingScreen(){

    val image = painterResource(R.drawable.autumn_telt_1)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(WhiteYellow)
    ){
        val scale = remember {
            Animatable(0.0f)
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
            painter = image,
            contentDescription = "Telt",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .scale(scale.value)

        )
    }

}
sealed class Screen(val route: String, val icon: ImageVector, val description: String) {
    object MapScreen : Screen("MapScreen", Icons.Default.Search, "Kart")
    object ListScreen : Screen("ListScreen", Icons.Default.Home, "List")
    object SavedScreen : Screen("SavedScreen", Icons.Default.Star,"Lagret")
    object ClothingScreen : Screen("ClothingScreen", Icons.Default.Favorite, "Lagret")
    object LoadingScreen : Screen("LoadingScreen", Icons.Default.Refresh,"Clothing")
    object InfoScreen : Screen("InfoScreen", Icons.Default.Info, "info")
}

/**Funksjonen lager en bottomNavBar som lar bruker navigere til SavedScreen og MapScreen
 * */@Composable
fun BottomNavBar(navController: NavController){
    val items = listOf(
        Screen.MapScreen,
        Screen.SavedScreen
    )


    NavigationBar(
        modifier = Modifier.fillMaxWidth()
            .height(80.dp),
        containerColor = ForestGreen
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                icon = { Icon(item.icon, contentDescription = item.route,
                    tint = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) Color.Black else Color.White) },
                label = { Text(text = item.description, color = Color.White, fontSize = 14.sp, fontWeight = Bold) },
                onClick = { if (currentDestination?.hierarchy?.any { it.route == item.route} == true) {
                    //onclick gjør ingentinger.
                } else {
                    navController.navigate(item.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                }
            )
        }
    }
}


/**
 * Hjelplefunksjon som navigerer brukeren til en ny skjerm.
 */
fun navigate(navController: NavController, route: String) {
    navController.navigate(route)
    {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}