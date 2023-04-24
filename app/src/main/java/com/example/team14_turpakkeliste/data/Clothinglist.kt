package com.example.team14_turpakkeliste.data

import ForecastData

//værdata gi beskjed om vindretning

fun getClothes(): List<Clothing>{
    val clothingList: List<Clothing> = listOf(
        //klær inspirert av stat-system fra ulvang på ullklær andre klær hentet fra Norrøna

        //ytterlagjakker
        //trenger ytterlag med vanntett 4!
        Clothing("Shell", "jacket","outer", 1, 6, 6, "goretexjacket"),
        Clothing("LightShell", "jacket", "outer", 1, 5, 6, "lightgoretextjacket"),
        Clothing("Down", "jacket", "outer", 5,3,5, "downjacket"),
        Clothing("Cotton", "jacket", "outer", 2, 2,5,"cottonjacket"),
        Clothing("Primaloft", "jacket", "outer",3, 3,5, "primaloft"),
        Clothing("Softshell", "jacket", "outer", 1, 2, 4, "windjacket"),
        Clothing("HeavyDown", "jacket", "outer", 6, 3, 6, "heavydown"),
        //mangler jakke og bukse for vann 4 og 5, men kan bare anbefale goretex her egt

        //ytterlag bukser
        Clothing("Shell", "pants", "outer", 1,6, 6,"goretexpants"),
        Clothing("LightShell", "pants", "outer", 1, 5, 6, "lightgoretextpants"),
        Clothing("Softshell", "pants", "outer", 2,3,5, "cottonpants"),
        Clothing("Softshell", "pants", "outer", 2, 4, 5, "heavypants"),
        Clothing("Softshell", "pants", "outer", 1, 3, 4, "trekkingpants"),
        Clothing("Softshell", "shorts", "outer", 1, 2, 4, "flexshorts"),

        //jakker uten vanntetthet
        Clothing("thermalFleece", "jacket", "outer", 5,1,4,"thermalfleece"),
        Clothing("thinFleece", "jacket", "outer", 2,1,3, "thinfleece"),
        Clothing("heavyWool", "jacket", "outer", 6, 1, 6, "heavywool"),
        Clothing("thinnestFleece", "jacket", "outer", 3,1,1, "thinnestfleece"),
        Clothing("mediumFleece", "jacket", "outer", 4, 1, 3, "mediumfleece"),
        //hvilken er denne?
        Clothing("Wool", "jacket", "outer", 4, 1, 3, "wooljacket"),

        //innerlag
        Clothing("Wool", "sweater", "inner" ,6, 1,1, "expeditionsweater"),
        Clothing("Wool", "pants", "inner", 6,1,1, "expeditionpants"),
        Clothing("Wool", "sweater", "inner", 5, 1,1, "warmsweater"),
        Clothing("Wool", "pants", "inner", 5,1,1, "warmpants"),
        Clothing("Wool", "sweater", "inner" ,4, 1,1, "thermosweater"),
        Clothing("Wool", "pants", "inner", 4,1,1, "thermopants"),
        //se gjennom imagenavn
        Clothing("Wool", "sweater", "inner", 3,1,1,"ravgenser"),
        Clothing("Wool", "pants", "inner", 3, 1, 1, "ravbukse"),
        Clothing("LightWool", "sweater", "inner", 2,1,1,"lightwoolsweater"),
        Clothing("LightWool", "pants", "inner", 2,1,1,"lightwoolpants"),
        Clothing("LightWool", "tshirt", "inner", 1, 1,1, "sommerull"),

        Clothing("Kan ikke anbefale noe her", "none", "none", 0, 0 ,0, "none")
    )
    return  clothingList
}
fun sortClothing(forecastData: ForecastData, dayNum: Int, layer: String): List<Clothing>{
    // Ta imot værdata og få som ouput outerReqMin, outerReqMax, innerReqMin og innerReqMax
    // Legg ved en boolean f.eks som sier om det er nedbør, kan være viktig for valg av klær, dersom man trenger varme, men ikke fra ytterlag.
    // Da verdsettes f.eks vannavstøtende kvaliteter, og et innerlag verdsetter høyere varme.
    //iterere gjennom og samle vann for hver time :D
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 40
        else -> 0
    }
    val date = forecastData.properties.timeseries.get(dataForDay).time
    println(date)
    val temp: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.air_temperature.toDouble()
    val wind: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.wind_speed.toDouble()
    var water = 0.0
    for(i in dataForDay..dataForDay+6){
        forecastData.properties.timeseries.get(i).data.next_1_hours.details?.precipitation_amount?.let {
            water += forecastData.properties.timeseries.get(i).data.next_1_hours.details!!.precipitation_amount.toDouble()
        }
    }
    val outerReqMin = chooseReqsOuter(temp, wind, water)
    val innerReqMin = chooseReqsInner(temp)
    if(outerReqMin.waterproof ==6 && temp >= -5.0){
        outerReqMin.warmth = 1
        innerReqMin.warmth += 2
    }
    if(outerReqMin.waterproof ==5 && temp >= -5.0){
        outerReqMin.warmth = 2
        innerReqMin.warmth += 1
    }
    //fyller liste med tomme kleselementer som informerer om at dert evt ikke finnes noen riktige plass dersom disse overskrives
    val tempList: MutableList<Clothing> = MutableList(2){ getClothes().get(getClothes().size-1) }
    for(clothing in getClothes()){
        val warmth: Int = clothing.warmth
        val wind: Int = clothing.windproof
        val water: Int = clothing.waterproof
        if(warmth == outerReqMin.warmth
            && wind >= outerReqMin.windproof
            && (water == outerReqMin.waterproof || water == outerReqMin.waterproof+1)
            && clothing.type == "jacket"
            && clothing.layer == layer){
                tempList.set(0,clothing)
                continue
        }
        if(warmth == outerReqMin.warmth
            && wind >= outerReqMin.windproof
            && (water == outerReqMin.waterproof || water == outerReqMin.waterproof+1)
            && (clothing.type == "pants" || clothing.type == "shorts")
            && clothing.layer == layer){
                tempList.set(1,clothing)
                continue
        }
        if(warmth == innerReqMin.warmth
            && (clothing.type == "sweater" || clothing.type == "tshirt")
            && clothing.layer == layer){
                tempList.set(0,clothing)
                continue
        }
        if(warmth == innerReqMin.warmth
            && clothing.type == "pants"
            && clothing.layer == layer){
                tempList.set(1,clothing)
                continue
            }
    }
    return tempList
}
fun chooseReqsOuter(temp: Double, wind: Double, water: Double?): MinRequirementsClothes{
    var warmth = 0
    var windproof = 0
    var waterproof = 0
    when(temp){
        //endre litt i disse til senere
        in -30.0..-20.0 -> warmth = 6
        in -19.9..-10.0 -> warmth = 5
        in -9.9..-0.1 -> warmth = 4
        in 0.0..9.9-> warmth = 3
        in 10.0..15.9-> warmth = 2
        in 16.0..30.0 -> warmth = 1
    }
    println(warmth)
    when(wind){
        in 0.0..2.5 -> windproof = 1
        in 2.6..5.0 -> windproof = 2
        in 5.1..7.5 -> windproof = 3
        in 7.6..10.0 -> windproof = 4
        in 10.1..12.5->windproof = 5
        in 12.6..32.7-> windproof = 6
    }
    println(wind)
    when(water!!){
        in 0.0..0.1 -> waterproof = 1
        in 0.2..0.3 -> waterproof = 2
        in 0.4..0.5 -> waterproof = 3
        in 0.6..0.8 -> waterproof = 4
        in 0.9..1.1 -> waterproof = 5
        in 1.2..50.0-> waterproof = 6
    }
    println(water)
    return MinRequirementsClothes(warmth,waterproof, windproof)
}
fun chooseReqsInner(temp: Double): MinRequirementsClothes{
    var warmth = 0
    when(temp){
        in -30.0..-20.0 -> warmth = 6
        in -19.9..-10.0 -> warmth = 5
        in -9.9..-0.1 -> warmth = 4
        in 0.0..9.9-> warmth = 3
        in 10.0..15.9-> warmth = 2
        in 16.0..30.0 -> warmth = 1
    }
    return MinRequirementsClothes(warmth,1,1)
}
fun getWeather(forecastData: ForecastData, dayNum: Int): String{
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 40
        else -> 0
    }
    val date = forecastData.properties.timeseries.get(dataForDay).time
    println(date)
    val temp: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.air_temperature.toDouble()
    val wind: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.wind_speed.toDouble()
    var water = 0.0
    for(i in dataForDay..dataForDay+6){
        forecastData.properties.timeseries.get(i).data.next_1_hours.details?.precipitation_amount?.let {
            water += forecastData.properties.timeseries.get(i).data.next_1_hours.details!!.precipitation_amount.toDouble()
        }
    }
    val returnString = "Det er meldt ${temp} grader \nog vind på ${wind} m/s \nDu kan forvente ${water} mm nedbør i løpet av dagen"
    return returnString
}