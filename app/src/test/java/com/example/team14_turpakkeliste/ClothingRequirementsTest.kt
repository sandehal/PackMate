package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.models.MinRequirementsClothes
import com.example.team14_turpakkeliste.data.chooseOuterClothingRequirements
import org.junit.Assert
import org.junit.Test

/**
 * tester at algoritmen som bestemmer minimumsverdier ved klær som kan anbefales
 * til bruker ved visse værverdier, fungerer som den skal
 */
class ClothingRequirementsTest {

    @Test
    fun outerRequirement_isCorrect(){
        val chooseClothingRequirementsOuter = chooseOuterClothingRequirements(16.1, 1.1, 0.2)
        val expectedRequirementsClothes = MinRequirementsClothes(1,2,1)
        Assert.assertEquals(expectedRequirementsClothes,chooseClothingRequirementsOuter)
    }
    @Test
    fun outerRequirement_isWrong(){
        val chooseClothingRequirementsOuter = chooseOuterClothingRequirements(16.1, 1.1, 0.2)
        val expectedRequirementsClothes = MinRequirementsClothes(2,3,3)
        Assert.assertNotEquals(expectedRequirementsClothes,chooseClothingRequirementsOuter)
    }
}