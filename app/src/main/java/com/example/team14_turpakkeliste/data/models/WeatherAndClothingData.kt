package com.example.team14_turpakkeliste.data.models


data class Clothing(val material: String, val type: String, val layer: String, val warmth: Int, val waterproof: Int, val windproof: Int, val image: String)
data class WeatherValues(val temp: Double, val windspeed: Double, val watermm: Double?)
data class MinRequirementsClothes(var warmth: Int, val waterproof: Int, val windproof: Int)

