package com.example.team14_turpakkeliste

import androidx.compose.runtime.Composable
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.team14_turpakkeliste.ui.ExpandableCard
import com.example.team14_turpakkeliste.ui.getImg
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpandableCardTest{
    
    @Test
    @Composable
    fun testExpandableCard(){
        ExpandableCard(title = "test", description = "test", img = "cottonjacket")
    }
}