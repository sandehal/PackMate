package com.example.team14_turpakkeliste.data

import ForecastData

fun getClothes(): List<Clothing>{
    var clothingList: List<Clothing> = listOf(
    Clothing("Shell", "jacket","outer", 0, 5, 5, "ShellJO"),
    Clothing("Shell", "jacket", "outer", 0,5,5, "ShellPO" ),
    Clothing("Down", "jacket", "outer", 4,2,3, "DownJO"),
    Clothing("Softshell", "jacket", "outer", 1, 2,4,"SoftJO"),
    Clothing("Softshell", "pants", "outer", 1,2,4, "SoftPO"))
    return  clothingList
}
fun sortClothing(jsonClothesList: List<Clothing>, forecastData: ForecastData): List<Clothing>{
    // Ta imot værdata og få som ouput outerReqMin, outerReqMax, innerReqMin og innerReqMax
    // Legg ved en boolean f.eks som sier om det er nedbør, kan være viktig for valg av klær, dersom man trenger varme, men ikke fra ytterlag.
    // Da verdsettes f.eks vannavstøtende kvaliteter, og et innerlag verdsetter høyere varme.
    val temp = forecastData.properties.meta.units.air_temperature.toDouble()
    val wind = forecastData.properties.meta.units.wind_speed.toDouble()
    val water = forecastData.properties.meta.units.precipitation_amount.toDouble()
    // evt ha en minimumsvarme som må nås med alle lag sin varme kombinert
    val outerReqMin: MinRequirementsClothes = chooseReqsOuter(temp, wind, water)
    // disse to verdiene kan også gjelde for sko/fottøy
    val outerReqMax = MaxRequirementsClothes(2,3, 5)
    val tempList: MutableList<Clothing> = mutableListOf()
    //val innerRequirement: MinRequirementsClothes = MinRequirementsClothes(3,3,3)
    for(clothing in jsonClothesList){
        val warmth: Int = clothing.warmth.toInt()
        //ikke glem å sjekke for vind og vann
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "jacket"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "pants"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "jacket"
            && clothing.layer == "inner"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "pants"
            && clothing.layer == "inner"
            && tempList.add(clothing))
            continue
    }
    return tempList
}
//precipicationamount forteller om nedbør
fun chooseReqsOuter(temp: Double, wind: Double, water: Double): MinRequirementsClothes{
    var warmth: Int = 0
    var windproof: Int = 0
    var waterproof: Int = 0
    when(temp){
        in -40.0..-21.0 -> warmth = 5
        in -20.0..-11.0 -> warmth = 4
        in -10.0..-1.0 -> warmth = 3
        in 0.0..9.0-> warmth = 2
        in 10.0..19.0-> warmth = 1
        in 20.0..40.0 -> warmth = 0
    }
    when(wind){
        //finne ut verdi for hvordan vær tolkes
        in 0.0..1.0 -> windproof = 0
        in 1.1..3.3 -> windproof = 1
        in 3.4..7.9 -> windproof = 2
        in 8.0..10.7 -> windproof = 3
        in 10.8..20.7 -> windproof = 4
        in 20.8..32.7 -> windproof = 5
    }
    when(water){
        in 0.0..0.2 -> waterproof = 0
        in 0.3..0.5 -> waterproof = 1
        in 0.6..1.0 -> waterproof = 2
        in 1.1..1.5 -> waterproof = 3
        in 1.6..2.0 -> waterproof = 4
        in 2.1..10.0 -> waterproof = 5
    }
    return MinRequirementsClothes(warmth,waterproof, windproof)
}