package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.WeatherValues
import org.junit.Test

class ClothingListNoneTest {
    /**
     * tester algoritmen som anbefaler klær med værverdier der det
     * ikke finnes aktuelle plagg å anbefale bruker enten ved for lave temperaturer
     * eller vindverdier som tilsvarer orkan
     **/
    @Test
    fun noneValidClothingPiece_isCorrect(){
        val inputWeatherValues =  WeatherValues(-40.0, 2.5, 0.0)
    }
}