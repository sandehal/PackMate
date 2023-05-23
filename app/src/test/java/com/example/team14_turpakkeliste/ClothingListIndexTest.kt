package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.getClothes
import org.junit.Assert
import org.junit.Test

class ClothingListIndexTest {
    @Test
    fun clothingPiece_isCorrect(){
        val expectedClothingPieceImg = "downjacket"
        val fetchedClothingPieceImg = getClothes()[2].image
        Assert.assertEquals(expectedClothingPieceImg, fetchedClothingPieceImg)
    }
    @Test
    fun clothingPiece_isWrong(){

    }
}