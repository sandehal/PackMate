package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.pinpointLocation
import org.junit.Test

import org.junit.Assert.*

/**
 * Testene sjekker om et punkt er inne i polygonet eller ikke. pinppointLocation brukes for metAlerts
 * */
class PolygonTest {
    @Test
    fun testPinpointLocationInsidePolygon() {
        val currentLatitude = 76.0
        val currentLongitude = -2.0
        val polygon = "75.0,-10.0 77.5,-10.0 77.5,0.0 75.0,0.0 75.0,-10.0"

        val result = pinpointLocation(currentLatitude, currentLongitude, polygon)

        assertEquals(true, result)
    }

    @Test
    fun testPinpointLocationOutsidePolygon() {
        val currentLatitude = 10.0
        val currentLongitude = 10.0
        val polygon = "75.0,-10.0 77.5,-10.0 77.5,0.0 75.0,0.0 75.0,-10.0"

        val result = pinpointLocation(currentLatitude, currentLongitude, polygon)

        assertEquals(false, result)
    }
}