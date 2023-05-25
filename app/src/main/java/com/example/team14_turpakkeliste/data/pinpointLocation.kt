package com.example.team14_turpakkeliste.data





/***
 * Finner ut om en gitt lokasjon er innefor, utenfor eller lik området til en gitt Alert.
returnerer true eller false.
 */

fun pinpointLocation(currentLatitude: Double,currentLongitude: Double, polygon : String): Boolean{

    val polygonAsArrayString = polygon.split(" ", ",")

    val polygonAsArray = polygonAsArrayString.map { it.toDouble() }.toTypedArray()
    var polygonMinValueX: Double = polygonAsArray[0]
    var polygonMaxValueX: Double = polygonAsArray[0]
    var polygonMinValueY: Double = polygonAsArray[1]
    var polygonMaxValueY: Double = polygonAsArray[1]
    var x = 0
    var y = 1

    while(polygonAsArray.size-2 >y){
        if(polygonMinValueX > polygonAsArray[x]){
            polygonMinValueX = polygonAsArray[x]
        }
        if(polygonMaxValueX < polygonAsArray[x]){
            polygonMaxValueX = polygonAsArray[x]
        }
        if(polygonMinValueY > polygonAsArray[y]){
        polygonMinValueY = polygonAsArray[y]
        }
        if(polygonMaxValueY < polygonAsArray[y]){
        polygonMaxValueY = polygonAsArray[y]
        }
        x += 2
        y += 2
    }
    if (currentLatitude in polygonMinValueX..polygonMaxValueX){
        if (currentLongitude in polygonMinValueY..polygonMaxValueY){
            return true
        }
    }
    return false
}