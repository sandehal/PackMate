package com.example.team14_turpakkeliste.ui

import android.content.Context
import android.util.Log
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val location = remember {
        mutableStateOf("")
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
            /*trailingIcon = {
        IconButton(onClick = { passwordHidden = !passwordHidden }) {
            val visibilityIcon =
                if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            // Please provide localized description for accessibility services
            val description = if (passwordHidden) "Show password" else "Hide password"
            Icon(imageVector = visibilityIcon, contentDescription = description)
        }
*/

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
    // Remember the GoogleMap instance
    val googleMap = remember {
        mutableStateOf<GoogleMap?>(null)
    }

    val clickedLatLng = remember {
        mutableStateOf<LatLng?>(null)
    }

    AndroidView({ mapView }) { view ->
        mapView.onCreate(null)
        mapView.onResume()
        // Get the GoogleMap instance if it's not already stored
        if (googleMap.value == null) {
            mapView.getMapAsync { map ->
                val norway = LatLng(62.943669, 9.917546)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(norway, 5f))
                map.addMarker(MarkerOptions().position(LatLng(59.297573, 10.420644)))
                map.setOnMapClickListener { latLng ->
                    clickedLatLng.value = latLng
                    // Call the API with the clicked LatLng here
                    Log.d("", clickedLatLng.value.toString())
                }
            }
        }
    }
}
fun getLocation(location: String, context: Context, mapView: MapView){


}







