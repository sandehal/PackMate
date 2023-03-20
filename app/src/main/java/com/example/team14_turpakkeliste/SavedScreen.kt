package com.example.team14_turpakkeliste


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.team14_turpakkeliste.ui.theme.Burgunder
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme



@Composable
fun SavedScreen() {

    Column() {

    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        BottomNavBarSaved()
    }

}


@Composable
fun BottomNavBarSaved(
) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Burgunder
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Saved") },
            label = { Text("Saved") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 0 }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SavedPreview() {
    Team14TurPakkeListeTheme {
        SavedScreen()
    }
}