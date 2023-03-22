package com.example.team14_turpakkeliste.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String?{
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use {it.readText() }
    }catch (ioException: IOException){
        ioException.printStackTrace()
        return null
    }
    return jsonString
}
fun showJsonAsList(context: Context, fileName: String): List<Clothing> {
    val jsonFileString = getJsonDataFromAsset(context, fileName)
    val gson = Gson()
    val listClothingType = object : TypeToken<List<Clothing>>() {}.type

    val clothes: List<Clothing> =  gson.fromJson(jsonFileString, listClothingType)
    return clothes
}