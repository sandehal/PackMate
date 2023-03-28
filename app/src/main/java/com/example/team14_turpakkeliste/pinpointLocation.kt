package com.example.team14_turpakkeliste

class pinpointLocation() {
    fun pinpointLocation(currentLocation : Pair<Int,Int>, polygon : String): Boolean{
        val polygonAsArrayString = polygon.split(" ")
        val polygonAsArray = polygonAsArrayString.map { it.toInt() }.toTypedArray()
        //minste og største verdi for polygonet.
        var polygonMinValueX: Int = polygonAsArray[0]
        var polygonMaxValueX: Int = polygonAsArray[0]
        var polygonMinValueY: Int = polygonAsArray[1]
        var polygonMaxValueY: Int = polygonAsArray[1]
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
        if (currentLocation.first in polygonMinValueX..polygonMaxValueX){
            if (currentLocation.second in polygonMaxValueY..polygonMinValueY){
                return true
            }
        }
        return false
    }
}