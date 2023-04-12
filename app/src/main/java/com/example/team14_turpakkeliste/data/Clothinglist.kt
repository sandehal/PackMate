package com.example.team14_turpakkeliste.data

import ForecastData
import kotlin.text.toDoubleOrNull

fun getClothes(): List<Clothing>{
    var clothingList: List<Clothing> = listOf(
        Clothing("Shell", "jacket","outer", 0, 2, 2, "ShellJO"),
        Clothing("Shell", "pants", "outer", 0,2, 2,"ShellPO" ),
        Clothing("Down", "jacket", "outer", 4,1,2, "DownJO"),
        Clothing("Softshell", "jacket", "outer", 2, 1,2,"SoftJO"),
        Clothing("Softshell", "pants", "outer", 2,1,2, "SoftPO"),
        Clothing("Primaloft", "jacket", "outer",3, 1,2, "PrimaJO")
    )
    return  clothingList
}
fun sortClothing(forecastData: ForecastData): List<Clothing>{
    // Ta imot værdata og få som ouput outerReqMin, outerReqMax, innerReqMin og innerReqMax
    // Legg ved en boolean f.eks som sier om det er nedbør, kan være viktig for valg av klær, dersom man trenger varme, men ikke fra ytterlag.
    // Da verdsettes f.eks vannavstøtende kvaliteter, og et innerlag verdsetter høyere varme.
    //iterere gjennom og samle vann for hver time :D
    val jsonClothesList: List<Clothing> = getClothes()
    val temp: Double = forecastData.properties.timeseries.get(0).data.instant.details.air_temperature.toDouble()
    val wind: Double = forecastData.properties.timeseries.get(0).data.instant.details.wind_speed.toDouble()
    var water: Double = 0.0
    forecastData.properties.timeseries.get(0).data.instant.details.precipitation_amount ?.let {
        water = forecastData.properties.timeseries.get(0).data.instant.details.precipitation_amount.toDouble()
    }
    // evt ha en minimumsvarme som må nås med alle lag sin varme kombinert
    val outerReqMin = chooseReqsOuter(temp, wind, water)
    // disse to verdiene kan også gjelde for sko/fottøy
    val outerReqMax = MaxRequirementsClothes(outerReqMin.warmth+1, outerReqMin.waterproof+1, outerReqMin.windproof+1)
    val tempList: MutableList<Clothing> = mutableListOf()
    for(clothing in jsonClothesList){
        val warmth: Int = clothing.warmth
        val wind: Int = clothing.windproof
        val water: Int = clothing.waterproof
        //ikke glem å sjekke for vind og vann
        if(outerReqMin.waterproof > water){
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && wind >= outerReqMin.windproof
            && wind <= outerReqMax.windproof
            && clothing.type == "jacket"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && wind >= outerReqMin.windproof
            && wind <= outerReqMax.windproof
            && clothing.type == "pants"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && wind >= outerReqMin.windproof
            && wind <= outerReqMax.windproof
            && clothing.type == "jacket"
            && clothing.layer == "inner"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && wind >= outerReqMin.windproof
            && wind <= outerReqMax.windproof
            && clothing.type == "pants"
            && clothing.layer == "inner"
            && tempList.add(clothing))
            continue
    }
    return tempList
}
//precipicationamount forteller om nedbør
fun chooseReqsOuter(temp: Double, wind: Double, water: Double?): MinRequirementsClothes{
    var warmth: Int = 0
    var windproof: Int = 0
    var waterproof: Int = 0
    when(temp){
        in -40.0..-21.0 -> warmth = 5
        in -20.9..-11.0 -> warmth = 4
        in -10.9..-1.0 -> warmth = 3
        in 0.9..9.9-> warmth = 2
        in 10.0..19.9-> warmth = 1
        in 20.0..40.9 -> warmth = 0
    }
    println(warmth)
    when(wind){
        //finne ut verdi for hvordan vær tolkes
        in 0.0..3.3 -> windproof = 0
        in 3.4..10.8 -> windproof = 1
        in 10.9..32.7 -> windproof = 2
    }
    println(wind)
    when(water!!){
        in 0.0..0.2 -> waterproof = 0
        in 0.3..1.0 -> waterproof = 1
        in 1.1..50.0 -> waterproof = 2
    }
    println(water)
    return MinRequirementsClothes(warmth,waterproof, windproof)
}
fun getWeather(forecastData: ForecastData): String{
    val temp: String = forecastData.properties.timeseries.get(0).data.instant.details.air_temperature.toString()
    val wind: String = forecastData.properties.timeseries.get(0).data.instant.details.wind_speed.toString()
    var water: String = "0.0"
    forecastData.properties.timeseries.get(0).data.instant.details.precipitation_amount ?.let {
        water = forecastData.properties.timeseries.get(0).data.instant.details.precipitation_amount.toString()
    }
    val returnString = "Det er meldt ${temp} grader \nog vind på ${wind} m/s \nDu kan forvente ${water} mm nedbør"
    return returnString
}