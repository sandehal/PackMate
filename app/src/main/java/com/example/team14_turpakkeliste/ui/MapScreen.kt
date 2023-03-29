package com.example.team14_turpakkeliste.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val location = remember {
        mutableStateOf("")
    }
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                location.value = ""
            },
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Clear",
                tint = Color.Black
            )
        }
    }

    Column {

        TextField(
            value = location.value,

            onValueChange = { location.value = it },

            placeholder = { Text(text = "Søk på området") },

            modifier = Modifier
                .padding(3.dp)
                .width(400.dp) //endre tilbake til 300 når vi bruker trailing
                .height(60.dp),

            singleLine = true,
            trailingIcon = if (location.value.isNotBlank()) trailingIconView else null,
        )
        DisplayMap()
    }
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ){
        makeListButton(navController)
        BottomNavBar(navController)
    }

}



@Composable
fun DisplayMap() {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map_view
        }
    }

    AndroidView({ mapView }) { view ->
        val googleMap = view.getMapAsync { map ->
            val norway = LatLng(62.943669,9.917546)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(norway, 5f))
            map.addMarker(MarkerOptions().position(LatLng(59.297573,10.420644)))
        }
        mapView.onCreate(null)
        mapView.onResume()
        //My location, enabled
//        mapView.getMapAsync { googleMap ->
//            googleMap.isMyLocationEnabled = true
//        }
    }
}


fun getLocation(location: String, context: Context, mapView: MapView){


}







