@file:Suppress("DEPRECATION", "DEPRECATION")

package com.example.team14_turpakkeliste.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.team14_turpakkeliste.TurViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

fun getNameFromLocation(cordinates: LatLng, viewModel: TurViewModel, context: Context): String {
    var locationName = ""
    var addressList : List<Address>? = null
    val geocoder = Geocoder(context)
    try {
        //Lønnet seg for større treffsikkerhet å legge til "Norway" hele to ganger.
        addressList = geocoder.getFromLocation(cordinates.latitude, cordinates.longitude, 1)
        println("Resultat")
    } catch (e: IOException) {
        e.printStackTrace()
        println("FEIL")
    }
    println(addressList)
    if (!addressList.isNullOrEmpty()) {
        val address = addressList[0]
        viewModel.currentLatitude = address.latitude
        viewModel.currentLongitude = address.longitude

        locationName = checkAvailabilityLoc(address)
        Log.d("Adressenavn", locationName)
        println(locationName)
        return locationName
    }
    return locationName
}



fun checkAvailabilityLoc(address: Address): String{

    if(address.subLocality != null){
        return address.subLocality.toString().replace("municipality", "kommune")
    }
    else if(address.subAdminArea!=null){
        return address.subAdminArea.toString().replace("municipality", "kommune")
    }
    else if(address.locality!=null){
        return address.locality.toString().replace("municipality", "kommune")
    }
    else if(address.adminArea!=null){

        return address.adminArea.toString().replace("municipality", "kommune")
    }
    else{
        return address.countryName.toString()
    }
}

fun getLocationCompose(location: String, viewModel: TurViewModel, context: Context): LatLng? {
    viewModel.location = location
    val latLng : LatLng?

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
    return null
}