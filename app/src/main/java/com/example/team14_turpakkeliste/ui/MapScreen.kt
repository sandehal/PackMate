package com.example.team14_turpakkeliste.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.TurViewModel
import com.example.team14_turpakkeliste.data.getLocationCompose
import com.example.team14_turpakkeliste.data.getNameFromLocation
import com.example.team14_turpakkeliste.ui.theme.ForestGreen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsComposeScreen(navController: NavController, viewModel: TurViewModel){

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
    val location = remember { mutableStateOf("") }
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

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            TextField(
                value = location.value,
                onValueChange = { location.value = it },
                placeholder = { Text(text = "Søk på område, eller trykk på kartet", fontSize = 14.sp, fontWeight = Bold) },
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    ,
                singleLine = true,
                trailingIcon = if (location.value.isNotBlank()) trailingIconView else null,
            )
            Button(
                onClick = {
                    val locCords = getLocationCompose(location.value, viewModel, context)
                    if (markerState == null) {
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
                shape = RoundedCornerShape(4.dp),
                colors =  ButtonDefaults.buttonColors(ForestGreen),
                modifier = Modifier
                    .height(60.dp)
                    .width(80.dp)
            ) {
                Text(text = "Søk", fontSize = 14.sp, fontWeight = Bold)
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    if (markerState == null) {
                        clickedLatLng.value = latLng
                        viewModel.currentLatitude = latLng.latitude
                        viewModel.currentLongitude = latLng.longitude
                        viewModel.location = getNameFromLocation(clickedLatLng.value!!, viewModel, context)
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(viewModel.currentLatitude, viewModel.currentLongitude), 9f)
                        scope.launch {
                            sheetState.show()
                        }
                    } else {
                        clickedLatLng.value = null
                    }
                },
            ) {
                if (markerState != null) {
                    Marker(state = markerState, onClick = markerClick)
                }
            }
        }

        BottomSheet(
            sheetState = sheetState,
            scope = scope,
            navController = navController,
            turViewModel = viewModel
        )

            BottomNavBar(navController)

    }



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(sheetState: SheetState, scope : CoroutineScope, navController: NavController, turViewModel: TurViewModel){
    val textLocation = turViewModel.checkIntitialized()
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Image(
                    painter = painterResource(id = R.drawable.mappet_ikon),
                    contentDescription = "Kart"
                )
                Text(
                    text = " $textLocation ",
                    fontWeight = Bold,
                    fontSize = 28.sp
                )
                Text(
                    text = "Hvor mange dager vil du på tur?",
                    fontWeight = Bold,
                    fontSize = 20.sp
                )
                var days by remember { mutableStateOf(0) }

                //vi må kanskje endre en del hvis spacer er forskjellig fra telefon til telefon
                var enabled1 by remember {
                    mutableStateOf(false)
                }
                var enabled2 by remember {
                    mutableStateOf(true)
                }
                var enabled3 by remember {
                    mutableStateOf(true)
                }
                Row {
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black,
                        disabledContainerColor = ForestGreen,
                        disabledContentColor = Color.White),
                        onClick = {
                            enabled1 = false
                            enabled2 = true
                            enabled3 = true
                            days = 1
                        },
                        enabled = enabled1
                    ) {
                        Text("1 dag", fontWeight = Bold)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black,
                            disabledContainerColor = ForestGreen,
                            disabledContentColor = Color.White),
                        onClick = {

                            enabled2 = false
                            enabled1 = true
                            enabled3 = true
                            days = 2
                        },
                        enabled = enabled2

                    ) {
                        Text("2 dager", fontWeight = Bold)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black,
                            disabledContainerColor = ForestGreen,
                            disabledContentColor = Color.White),
                        onClick = {
                            enabled3 = false
                            enabled1 = true
                            enabled2 = true
                            days = 3
                        },
                        enabled = enabled3
                    ) {
                        Text("3 dager", fontWeight = Bold)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
                //hvordan fungerer denne? hilsen Trym
                    if(days > 3){
                        turViewModel.updateDays(2)
                    }
                    else if(days < 1){
                        turViewModel.updateDays(0)
                    }
                    else{
                        turViewModel.updateDays(days -1)
                    }
            }
            //DropdownMenu(turViewModel)
            //DatePickerScreen()
            Spacer(modifier = Modifier.height(20.dp))
            MakeListButton(navController, turViewModel)
            Spacer(modifier = Modifier.height(20.dp))
        }


    }
}

@Composable
fun MakeListButton(navController: NavController, turViewModel: TurViewModel){
    ExtendedFloatingActionButton(
        containerColor = ForestGreen,
        contentColor = Color.White,
        icon = { Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null) },
        text = { Text("Motta pakkeliste for valgt lokasjon. ", fontSize = 16.sp, fontWeight = Bold) },
        onClick = { turViewModel.getForecast(); navController.navigate("ListScreen")
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}













