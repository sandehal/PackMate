package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.BottomNavBar
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.example.team14_turpakkeliste.ui.theme.Team14TurPakkeListeTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


@Composable
fun MapScreen(navController: NavController) {

    Column {
        DisplayMap()
    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        BottomNavBar(navController)
    }

}

@Composable
fun DisplayMap() {
    val activity = LocalContext.current as FragmentActivity
    AndroidView(
        factory = { activity.layoutInflater.inflate(R.layout.activity_main, null, false) }
    ) { view ->
        val mapFragment = activity.supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        //Controlling the map here at the moment. We should probably change this.
        mapFragment.getMapAsync { googleMap ->
            val norway = LatLng(62.943669,9.917546)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(norway, 5f))
            googleMap.addMarker(MarkerOptions().position(LatLng(59.297573,10.420644)))
        }
    }
}







