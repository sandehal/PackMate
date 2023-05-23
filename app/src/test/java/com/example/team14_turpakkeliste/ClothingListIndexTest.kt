package com.example.team14_turpakkeliste

import com.example.team14_turpakkeliste.data.getClothes
import org.junit.Assert
import org.junit.Test

class ClothingListIndexTest {
    /*
        her kan vi evt teste sorter clothes og vise at det ikke forandrer seg
     */
    @Test
    fun clothingPiece_isCorrect(){
        val expectedClothingPieceImg = "downjacket"
        val fetchedClothingPieceImg = getClothes()[2].image
        Assert.assertEquals(expectedClothingPieceImg, fetchedClothingPieceImg)
    }
    @Test
    fun clothingPiece_isWrong(){
        val notExpectedClothingPieceImg = "cottonjacket"
        val fetchedClothingPieceImg = getClothes()[4].image
        Assert.assertNotEquals(notExpectedClothingPieceImg, fetchedClothingPieceImg)
    }
}