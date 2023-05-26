package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.models.WeatherValues
import com.example.team14_turpakkeliste.data.sortClothing
import org.junit.Assert
import org.junit.Test

/**
 * Tester om det finnes et ytterplagg for ulike vær-scenarioer. Eks ekstremt lave temperaturer,
 * høy varme. Ekstremt regnvær, vindvær og i lett regn i med høytemperatur
 * */
class ExistingClothesForWeatherValuesTest {

    @Test
    fun testClothingExistsLowTemperature(){
        val inputWeatherValues =  WeatherValues(-29.0, 10.5, 0.0)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val unexpectedClothing = "plagg"
        Assert.assertNotEquals(unexpectedClothing, sortedClothes[0].type)
    }
    @Test
    fun testClothingExistsHighTemperature(){
        val inputWeatherValues =  WeatherValues(25.0, 5.5, 0.0)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val unexpectedClothing = "plagg"
        Assert.assertNotEquals(unexpectedClothing, sortedClothes[0].type)
    }
    @Test
    fun testClothingExistsInHeavyRain(){
        val inputWeatherValues =  WeatherValues(5.0, 5.5, 5.5)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val unexpectedClothing = "plagg"
        Assert.assertNotEquals(unexpectedClothing, sortedClothes[0].type)
    }
    @Test
    fun testClothingExistsInHeavyWind(){
        val inputWeatherValues =  WeatherValues(10.0, 17.5, 0.0)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val unexpectedClothing = "plagg"
        Assert.assertNotEquals(unexpectedClothing, sortedClothes[0].type)
    }
    @Test
    fun testClothingDontExistsInLightRainWarmTemp(){
        val inputWeatherValues =  WeatherValues(20.0, 3.5, 0.3)
        val sortedClothes = sortClothing("outer", inputWeatherValues)
        val unexpectedClothing = "plagg"
        Assert.assertNotEquals(unexpectedClothing, sortedClothes[0].type)
    }
}