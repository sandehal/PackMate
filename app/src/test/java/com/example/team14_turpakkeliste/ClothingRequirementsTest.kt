package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.MinRequirementsClothes
import com.example.team14_turpakkeliste.data.chooseReqsOuter
import org.junit.Assert
import org.junit.Test

class ClothingRequirementsTest {
    @Test
    fun outerRequirement_isCorrect(){
        Assert.assertEquals(chooseReqsOuter(16.1, 1.1, 0.2), MinRequirementsClothes(1, 1, 2))
    }
    @Test
    fun outerRequirement_isWrong(){
        Assert.assertNotEquals(chooseReqsOuter(16.1, 1.1, 0.2), MinRequirementsClothes(2, 3, 3))
    }
}