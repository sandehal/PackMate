package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.WeatherValues
import com.example.team14_turpakkeliste.data.sortClothing
import org.junit.Assert
import org.junit.Test

/**
 * noneValidClothingPiece_isCorrect() tester algoritmen som anbefaler klær med værverdier der det
 * ikke finnes aktuelle plagg å anbefale bruker enten ved for lave temperaturer
 * eller vindverdier som tilsvarer orkan.
 *
 * validClothingPiece_isCorret() tester at for det finnes klær vi kan anbefale til
 * bruker tross kulde og sterk vind. Ettersom vi har satt en begrensning på temperaturer lavere enn
 * -30.0 grader celsius
 **/
class ClothingListNoneTest {

    @Test
    fun noneValidClothingPiece_isCorrect(){
        val inputWeatherValues =  WeatherValues(-40.0, 2.5, 0.0)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val expectedClothing = "none"
        Assert.assertEquals(expectedClothing, sortedClothes.get(0).type)
    }
}