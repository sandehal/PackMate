package com.example.team14_turpakkeliste.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.team14_turpakkeliste.BottomNavBar
import com.example.team14_turpakkeliste.MakeListButton
import com.example.team14_turpakkeliste.data.Alert
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsComposeScreen(navController: NavController, viewModel: TurViewModel, alerts: List<Alert>){

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val norway = LatLng(59.911491, 10.757933)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 6f)
    }


    val clickedLatLng = remember {
        mutableStateOf<LatLng?>(null)
    }

    DisposableEffect(key1 = navController.currentBackStackEntry) {
        onDispose {
            clickedLatLng.value = null
        }
    }


    val markerState = clickedLatLng.value?.let { rememberMarkerState(position = it) }

    //Relatert til bottomSheet
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val markerClick: (Marker) -> Boolean = {
        if (markerState != null) {
            Log.d("pos", "${markerState.position.latitude}, ${markerState.position.longitude} ")
        }
        scope.launch {
            sheetState.show()
        }
        false
    }

    val baseLatLng = LatLng(60.47202399999999, 8.468945999999999)

    //Hentet fra Sander
    val location = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current




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



    Column(modifier = Modifier.fillMaxSize()) {
        Row() {
            TextField(
                value = location.value,

                onValueChange = { location.value = it},
                placeholder = { Text(text = "Søk på område") },

                modifier = Modifier
                    .padding()
                    .width(300.dp)
                    .height(60.dp),

                singleLine = true,
                trailingIcon = if (location.value.isNotBlank()) trailingIconView else null,
            )
            // on below line adding a button.
            Button(
                onClick = {

                    val locCords = getLocationCompose(location.value, viewModel, context)
                    if(markerState == null) {
                        if (location.value != "") {
                            if (locCords != null && locCords != baseLatLng) {
                                clickedLatLng.value = locCords

                                cameraPositionState.position = CameraPosition.fromLatLngZoom(locCords, 9f)
                                scope.launch {
                                    sheetState.show()
                                }
                            }
                        }
                    } else {
                        clickedLatLng.value = null

                    }
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(ForestGreen),
                // on below line adding a modifier for our button.
                modifier = Modifier

                    .height(60.dp)
                    .fillMaxSize()
            )
            {
                Text("Søk")
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 50.dp))
            .padding(bottom = 70.dp, top = 60.dp)
    ) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->

                if (markerState == null) {

                    clickedLatLng.value = latLng
                    viewModel.currentLatitude = latLng.latitude
                    viewModel.currentLongitude = latLng.longitude
                    viewModel.getForecast(alerts = alerts)
                    Log.d(
                        "Oppdatert",
                        "${viewModel.currentLatitude}, ${viewModel.currentLongitude}"
                    )
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(viewModel.currentLatitude, viewModel.currentLongitude), 9f)
                    scope.launch {
                        sheetState.show()
                    }

                } else {

                    clickedLatLng.value = null

                    //potensiell bug er at man ikke fjerner de gamle lat long verdiene fra view.
                    //Men de oppdateres for hver gang man plasserer en ny.
                }
            },
        )
        {

            if (markerState != null) {
                Marker(state = markerState, onClick = markerClick)

            }
        }

        BottomSheet(
            coordinates = clickedLatLng.value.toString(),
            sheetState = sheetState,
            scope = scope,
            navController = navController, turViewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom) {
        BottomNavBar(navController)
    }
}

fun getLocationCompose(location: String, viewModel: TurViewModel, context: Context): LatLng? {

    var latLng : LatLng?
    latLng = null
    var addressList : List<Address>? = null
    Log.d("Location",
        location
    )
    val geocoder = Geocoder(context)
    try {

        //Lønnet seg for større treffsikkerhet å legge til "Norway" hele to ganger.

        addressList = geocoder.getFromLocationName(location.plus(", Norway"), 1)
        println("Resultat")
    } catch (e: IOException) {
        e.printStackTrace()
        println("FEIL")
    }

    if (addressList!!.isNotEmpty()) {
        val address = addressList[0]
        viewModel.currentLatitude = address.latitude
        viewModel.currentLongitude = address.longitude
        latLng = LatLng(address.latitude,address.longitude)
        Log.d("adressekord",
            "${viewModel.currentLatitude}, ${viewModel.currentLongitude}")

        return latLng
    }
    return latLng
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(coordinates: String, sheetState: SheetState, scope : CoroutineScope, navController: NavController, turViewModel: TurViewModel){


    if (sheetState.isVisible){
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch{
                    sheetState.hide()
                }
            },
        )

        {
            MakeListButton(navController)
            DateRangePickerScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerScreen() {

    val dateTime = LocalDateTime.now()

    val dateRangePickerState = remember {
        DateRangePickerState(
            initialSelectedStartDateMillis = dateTime.toMillis(),
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = dateTime.plusDays(1).toMillis(),
            initialDisplayMode = DisplayMode.Input,
            yearRange = (2023..2023),
        )
    }
    DateRangePicker(state = dateRangePickerState, title = { "Choose timeframe for your journey!" })
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
















