package com.example.team14_turpakkeliste.data

import com.example.team14_turpakkeliste.data.models.Clothing
import com.example.team14_turpakkeliste.data.models.ForecastData
import com.example.team14_turpakkeliste.data.models.MinRequirementsClothes
import com.example.team14_turpakkeliste.data.models.WeatherValues


/**
 * Returnerer Liste med klesobjekter
 * */
fun getClothes(): List<Clothing>{
    return listOf(
        //ytterlag jakker
        Clothing("Goretex", "jakke","outer", 1, 6, 6, "goretexjacket"),
        Clothing("LightShell", "jakke", "outer", 1, 5, 6, "lightgoretexjacket"),
        Clothing("Dun", "jakke", "outer", 5,3,5, "downjacket"),
        Clothing("Bomull", "jakke", "outer", 2, 2,5,"cottonjacket"),
        Clothing("Primaloft", "jakke", "outer",3, 3,5, "primaloft"),
        Clothing("Softshell", "jakke", "outer", 1, 2, 4, "windjacket"),
        Clothing("Varm Dun", "jakke", "outer", 6, 3, 6, "heavydown"),
        Clothing("Fleece", "jakke", "outer", 3,2,4, "alphajacket"),
        Clothing("ShellDun", "jakke", "outer", 6,6,6, "shelldownaparka"),
        Clothing("MediumDun", "jakke", "outer", 4,3,6, "mediumdown"),

        //ytterlag bukser
        Clothing("Shell", "bukse", "outer", 1,6, 6,"goretexpants"),
        Clothing("Lett Shell", "bukse", "outer", 1, 5, 6, "lightgoretexpants"),
        Clothing("Softshell", "bukse", "outer", 2,3,5, "cottonpants"),
        Clothing("Softshell", "bukse", "outer", 2, 4, 5, "heavypants"),
        Clothing("Softshell", "bukse", "outer", 1, 3, 4, "trekkingpants"),
        Clothing("Softshell", "shorts", "outer", 1, 2, 4, "flexshorts"),

        //ikke-vanntette jakker
        Clothing("Tykk Fleece", "jakke", "outer", 5,1,4,"thermalfleece"),
        Clothing("Tynn Fleece", "jakke", "outer", 2,1,3, "thinfleece"),
        Clothing("Tykk Ull", "jakke", "outer", 6, 1, 6, "heavywool"),
        Clothing("Svært tynn Fleece", "jakke", "outer", 3,1,1, "thinnestfleece"),
        Clothing("Medium tykk Fleece", "jakke", "outer", 4, 1, 3, "mediumfleece"),
        Clothing("Ull", "jakke", "outer", 4, 1, 4, "wooljacket"),

        //innerlag
        Clothing("Ull", "genser", "inner" ,6, 1,1, "expeditionsweater"),
        Clothing("Ull", "bukse", "inner", 6,1,1, "expeditionpants"),
        Clothing("Ull", "genser", "inner", 5, 1,1, "warmsweater"),
        Clothing("Ull", "bukse", "inner", 5,1,1, "warmpants"),
        Clothing("Ull", "genser", "inner" ,4, 1,1, "thermosweater"),
        Clothing("Ull", "bukse", "inner", 4,1,1, "thermopants"),
        Clothing("Ull", "genser", "inner", 3,1,1,"mediumwoolsweater"),
        Clothing("Ull", "bukse", "inner", 3, 1, 1, "mediumwoolpants"),
        Clothing("Sommerull", "genser", "inner", 2,1,1,"lightwoolsweater"),
        Clothing("Sommerull", "tskjorte", "inner", 1, 1,1, "sommerull"),
        Clothing("ull", "boxer", "inner", 1,1,1, "ullboxer"),

        Clothing("Kan ikke anbefale noe ", "plagg", "none", 0, 0 ,0, "none")
    )
}
/**
 * Algoritmen som står for å returnere liste med to plagg. Denne gjør dette basert på lag og værverdier
 *
 * Den gjør kall på choose(Outer/Inner)ClothingRequirements og bruker verdiene returnert som minimumsverdier for plaggenes
 * egenskaper/resistensverdier.
 * For bukser, settes minimumsverdien for varme som 2 ettersom dette er det dataen tilbyr.
 * For hvert plagg i lista bli det sjekket om plagget når de nødevendige minimumsverdiene. Dersom plagget
 * har de nøvendige verdiene settes det inn i lista.
 * */
fun sortClothing(layer: String, weatherValues: WeatherValues): List<Clothing>{
    //endre disse til bedre navn
    val temp = weatherValues.temp
    val wind = weatherValues.windspeed
    val water = weatherValues.watermm
    val outerReqMin = chooseOuterClothingRequirements(temp, wind, water)
    val outerReqPants = chooseOuterClothingRequirements(temp,wind,water)
    val innerReqMin = chooseInnerClothingRequirements(temp, water)
    if(outerReqPants.warmth > 2){
        outerReqPants.warmth = 2
    }
    val tempList: MutableList<Clothing> = MutableList(2){ getClothes()[getClothes().size-1] }
    for(clothing in getClothes()){
        val warmth: Int = clothing.warmth
        val windspeed: Int = clothing.windproof
        val watermilimeter: Int = clothing.waterproof
        if(warmth == outerReqMin.warmth
            && windspeed >= outerReqMin.windproof
            && (watermilimeter == outerReqMin.waterproof || watermilimeter == outerReqMin.waterproof+1)
            && clothing.type == "jakke"
            && clothing.layer == layer){
            tempList[0] = clothing
                continue
        }
        if(warmth == outerReqPants.warmth
            && windspeed >= outerReqPants.windproof
            && (watermilimeter == outerReqPants.waterproof || watermilimeter == outerReqPants.waterproof+2)
            && (clothing.type == "bukse" || clothing.type == "shorts")
            && clothing.layer == layer){
            tempList[1] = clothing
                continue
        }
        if(warmth == innerReqMin.warmth
            && (clothing.type == "genser" || clothing.type == "tskjorte")
            && clothing.layer == layer){
            tempList[0] = clothing
            continue
        }
        if(warmth == innerReqMin.warmth
            && (clothing.type == "bukse" || clothing.type == "boxer")
            && clothing.layer == layer){
            tempList[1] = clothing
            continue
        }
    }
    return tempList
}
/**
 * Bestemmer minimumsverdier for klær som kan være aktuelle å anbefale basert på temperatur, vind og vann.
 *
 * Ved tilfeller der det er nødvendig med helt vanntette plagg over -5 grader celsius vil dette trumfe
 * varme-nødvendigheten og vanntetthet settes høyere.
 * */
fun chooseOuterClothingRequirements(temperature: Double, wind: Double, water: Double?): MinRequirementsClothes {
    var warmth = when(temperature) {
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
        in 0.9..1.4 ->  5
        in 1.5..50.0->  6
        else -> {0}
    }
    if(waterproof >= 5 && warmth <= 4){
        warmth = 1
    }
    return MinRequirementsClothes(warmth,waterproof, windproof)
}
/**
 * Bestemmer minimumsverdier for klær som kan være aktuelle å anbefale basert på temperatur og vannmengde.
 *
 * Dersom det er over -5 grader celsius og nødvendig med 100% vanntett ytterplagg vil varmebehovet
 * for innerlag økes.
 * */
fun chooseInnerClothingRequirements(temperature: Double, water: Double?): MinRequirementsClothes {
    var warmth = when(temperature) {
        in -30.0..-20.0 -> 6
        in -19.9..-10.0 ->  5
        in -9.9..-0.1 ->  4
        in 0.0..9.9->  3
        in 10.0..15.9->  2
        in 16.0..30.0 ->  1
        else -> {0}
    }
    if (water != null) {
        if(water >= 0.9 && warmth <= 4){
            warmth += 2
        }
    }
    return MinRequirementsClothes(warmth, 1,1)
}
/**
 * Returnerer verdier for varme, vind og nedbør for valgt område og dato
 */
fun getWeather(forecastData: ForecastData, dayNum: Int): WeatherValues {
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 48
        else -> 0
    }
    val temp: Double = forecastData.properties.timeseries[dataForDay].data.instant.details.air_temperature.toDouble()
    val wind: Double = forecastData.properties.timeseries[dataForDay].data.instant.details.wind_speed.toDouble()
    var water = 0.0
    for(i in dataForDay..dataForDay+2){
        forecastData.properties.timeseries[i].data.next_1_hours.details?.precipitation_amount?.let {
            water += forecastData.properties.timeseries[i].data.next_1_hours.details!!.precipitation_amount.toDouble()
        }
    }
    return WeatherValues(temp, wind, water)
}

/**
 * Returnerer en string som brukes for å bestemme værikonet for valgt område og dato
 */
fun getweatherIcon(forecastData: ForecastData, dayNum: Int): String{
    val dataForDay = when(dayNum){
        0 -> 2
        1 -> 26
        2 -> 48
        else -> 0
    }
    return forecastData.properties.timeseries[dataForDay].data.next_1_hours.summary.symbol_code
}