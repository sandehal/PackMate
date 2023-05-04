package com.example.team14_turpakkeliste.data

fun getClothes(): List<Clothing>{
    return listOf(
        //klær inspirert av stat-system fra ulvang på ullklær andre klær hentet fra Norrøna
        //ytterlagjakker
        Clothing("Shell", "jacket","outer", 1, 6, 6, "goretexjacket"),
        Clothing("LightShell", "jacket", "outer", 1, 5, 6, "lightgoretexjacket"),
        Clothing("Down", "jacket", "outer", 5,3,5, "downjacket"),
        Clothing("Cotton", "jacket", "outer", 2, 2,5,"cottonjacket"),
        Clothing("Primaloft", "jacket", "outer",3, 3,5, "primaloft"),
        Clothing("Softshell", "jacket", "outer", 1, 2, 4, "windjacket"),
        Clothing("HeavyDown", "jacket", "outer", 6, 3, 6, "heavydown"),
        Clothing("Alpha100", "jacket", "outer", 3,2,4, "alphajacket"),
        Clothing("ShellDown", "jacket", "outer", 6,6,6, "shelldownaparka"),
        Clothing("MediumDown", "jacket", "outer", 4,3,6, "mediumdown"),
        //ytterlag bukser
        Clothing("Shell", "pants", "outer", 1,6, 6,"goretexpants"),
        Clothing("LightShell", "pants", "outer", 1, 5, 6, "lightgoretexpants"),
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
        Clothing("Wool", "jacket", "outer", 4, 1, 4, "wooljacket"),
        //innerlag
        Clothing("Wool", "sweater", "inner" ,6, 1,1, "expeditionsweater"),
        Clothing("Wool", "pants", "inner", 6,1,1, "expeditionpants"),
        Clothing("Wool", "sweater", "inner", 5, 1,1, "warmsweater"),
        Clothing("Wool", "pants", "inner", 5,1,1, "warmpants"),
        Clothing("Wool", "sweater", "inner" ,4, 1,1, "thermosweater"),
        Clothing("Wool", "pants", "inner", 4,1,1, "thermopants"),
        //se gjennom imagenavn
        Clothing("Wool", "sweater", "inner", 3,1,1,"mediumwoolsweater"),
        Clothing("Wool", "pants", "inner", 3, 1, 1, "mediumwoolpants"),
        Clothing("LightWool", "sweater", "inner", 2,1,1,"lightwoolsweater"),
        Clothing("LightWool", "pants", "inner", 2,1,1,"lightwoolpants"),
        Clothing("LightWool", "tshirt", "inner", 1, 1,1, "sommerull"),
        Clothing("Lightwool", "boxer", "inner", 1,1,1, "woolboxer"),

        Clothing("Kan ikke anbefale noe her", "none", "none", 0, 0 ,0, "none")
    )
}
fun sortClothing(layer: String, weatherValues: WeatherValues): List<Clothing>{
    //val date = forecastData.properties.timeseries.get(dataForDay).time
    val temp = weatherValues.temp
    val wind = weatherValues.windspeed
    val water = weatherValues.watermm
    //disse burde ikke være avhengige av hverandre
    val outerReqMin = chooseReqsOuter(temp, wind, water)
    val outerReqPants = chooseReqsOuter(temp,wind,water)
    val innerReqMin = chooseInnerReqs(temp, water)
    if(outerReqPants.warmth > 2){
        outerReqPants.warmth = 2
    }
    //fyller liste med tomme kleselementer som informerer om at dert evt ikke finnes noen riktige plass dersom disse overskrives
    val tempList: MutableList<Clothing> = MutableList(2){ getClothes().get(getClothes().size-1) }
    for(clothing in getClothes()){
        //endre disse variablene til resistance
        val warmth: Int = clothing.warmth
        val windspeed: Int = clothing.windproof
        val watermilimeter: Int = clothing.waterproof
        //endre metoden til å sjekke for plagg først og sorter basert på det ,ikke varme! aka flere if loops
        //if(clothing.type == "jacket"){ }
        //if(clothing.type == "sweater" || clothing.type == "tshirt"){ }
        //if(clothing.type == "pants" || clothing.type == "shorts"){ }
        if(warmth == outerReqMin.warmth
            && windspeed >= outerReqMin.windproof
            && (watermilimeter == outerReqMin.waterproof || watermilimeter == outerReqMin.waterproof+1)
            && clothing.type == "jacket"
            && clothing.layer == layer){
                tempList.set(0,clothing)
                continue
        }
        //fiks slik at den kan velge bukser og bare anbefale varmere underlag!
        if(warmth == outerReqPants.warmth
            && windspeed >= outerReqPants.windproof
            && (watermilimeter == outerReqPants.waterproof || watermilimeter == outerReqPants.waterproof+2)
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
            && (clothing.type == "pants" || clothing.type == "boxer")
            && clothing.layer == layer){
                tempList.set(1,clothing)
            continue
        }
    }
    return tempList
}
//lag en egen metode for innerlag også trym!
//test alt dette i intellij
fun chooseReqsOuter(temp: Double, wind: Double, water: Double?): MinRequirementsClothes{
    //maks og mintemp for å være med å avgjøre
    var warmth = when(temp) {
        in 16.0..30.0 ->  1
        in 10.0..15.9->  2
        in 0.0..9.9->  3
        in -9.9..-0.1 ->  4
        in -19.9..-10.0 ->  5
        in -30.0..-20.0 -> 6
        else -> {0}
    }
    val windproof = when(wind){
        in 0.0..2.5 ->  1
        in 2.6..5.0 ->  2
        in 5.1..7.5 ->  3
        in 7.6..10.0 -> 4
        in 10.1..17.5-> 5
        in 17.6..32.7-> 6
        else -> {0}
    }
    val waterproof = when(water!!){
        in 0.0..0.1 ->  1
        in 0.2..0.3 ->  2
        in 0.4..0.8 ->  3
        //det finnes ikke tilstrekkelig data som tilsier at vi kan ha klær osm har vanntetthet 4
        //Dette må vi da evt "lage" selv ller bare drite i
        //in 0.6..0.8 -> 4
        in 0.9..1.4 ->  5
        in 1.5..50.0->  6
        else -> {0}
    }
    if(waterproof >= 5 && warmth <= 4){
        warmth = 1
    }
    return MinRequirementsClothes(warmth,waterproof, windproof)
}
fun chooseInnerReqs(temp: Double, water: Double?): MinRequirementsClothes{
    var warmth = when(temp) {
        in -30.0..-20.0 -> 6
        in -19.9..-10.0 ->  5
        in -9.9..-0.1 ->  4
        in 0.0..9.9->  3
        in 10.0..15.9->  2
        in 16.0..30.0 ->  1
        else -> {0}
    }
    if (water != null) {
        if(water >= 0.9 && temp <= 4){
            warmth += 2
        }
    }
    return MinRequirementsClothes(warmth, 1,1)
}
//fiks denne slik at den henter ut riktig data på dato!!!!
fun getWeather(forecastData: ForecastData, dayNum: Int): WeatherValues{
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 48
        else -> 0
    }
    //val date = forecastData.properties.timeseries.get(dataForDay).time
    //println(date)
    val temp: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.air_temperature.toDouble()
    val wind: Double = forecastData.properties.timeseries.get(dataForDay).data.instant.details.wind_speed.toDouble()
    var water = 0.0
    //her kunne vi tatt avgjørelsen å ha maxprecipitation fordi denne metoden er så av og på :/ kan kræsje når det er ny time osv
    for(i in dataForDay..dataForDay+2){
        forecastData.properties.timeseries.get(i).data.next_1_hours.details?.precipitation_amount?.let {
            water += forecastData.properties.timeseries.get(i).data.next_1_hours.details!!.precipitation_amount.toDouble()
        }
    }
    return WeatherValues(temp, wind, water)
}
fun getweatherIcon(forecastData: ForecastData, dayNum: Int): String{
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 48
        else -> 0
    }
    return forecastData.properties.timeseries.get(dataForDay).data.next_1_hours.summary.symbol_code
}