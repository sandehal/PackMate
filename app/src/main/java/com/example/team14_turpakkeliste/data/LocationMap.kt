@file:Suppress("DEPRECATION", "DEPRECATION")

package com.example.team14_turpakkeliste.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.team14_turpakkeliste.TurViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException


/**
 * Funksjonen returnerer stedsnavn basert på koordinater den får inn som parametre.
 * */
fun getNameFromLocation(cordinates: LatLng, viewModel: TurViewModel, context: Context): String {
    val locationName: String
    var addressList : List<Address>? = null
    val geocoder = Geocoder(context)
    try {

        addressList = geocoder.getFromLocation(cordinates.latitude, cordinates.longitude, 1)

    } catch (e: IOException) {
        e.printStackTrace()

    }

    return if (!addressList.isNullOrEmpty()) {
        val address = addressList[0]
        viewModel.currentLatitude = address.latitude
        viewModel.currentLongitude = address.longitude

        locationName = checkAvailabilityLoc(address)

        locationName
    }else{
        "Nå er du på bærtur!"
    }
}


/**
 * Funksjonen returner den mest nøyaktige addressen basert på address-objektet den får som parameter.
 * */
fun checkAvailabilityLoc(address: Address): String{

    if(address.subLocality != null){
        return if (address.subLocality.toString().contains("Municipality")){
            address.subLocality.toString().replace("Municipality", "kommune")

        }
        else{
            address.subLocality.toString().replace("municipality", "kommune")
        }
    }
    else if(address.subAdminArea!=null){
        return if (address.subAdminArea.toString().contains("Municipality")){
            address.subAdminArea.toString().replace("Municipality", "kommune")

        }else{
            address.subAdminArea.toString().replace("municipality", "kommune")
        }    }
    else if(address.locality!=null){
        return if (address.locality.toString().contains("Municipality")){
            address.locality.toString().replace("Municipality", "kommune")

        }else{
            address.locality.toString().replace("municipality", "kommune")
        }    }
    else if(address.adminArea!=null){

        return if (address.adminArea.toString().contains("Municipality")){
            address.adminArea.toString().replace("Municipality", "kommune")

        }else{
            address.adminArea.toString().replace("municipality", "kommune")
        }    }
    else{
        return address.countryName.toString()
    }
}

/**
 * Funksjonen returnerer latitude and longitude basert på lokasjonen den får som parameter
 * */
fun getLocationCompose(location: String, viewModel: TurViewModel, context: Context): LatLng? {
    viewModel.location = location
    val latLng : LatLng?

    var addressList : List<Address>? = null

    val geocoder = Geocoder(context)
    try {


        addressList = geocoder.getFromLocationName(location.plus(", Norway"), 1)

    } catch (e: IOException) {
        e.printStackTrace()

    }

    if (addressList!!.isNotEmpty()) {
        val address = addressList[0]
        viewModel.currentLatitude = address.latitude
        viewModel.currentLongitude = address.longitude
        latLng = LatLng(address.latitude,address.longitude)




        return latLng
    }
    return null
}