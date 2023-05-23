package com.example.team14_turpakkeliste

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.team14_turpakkeliste.ui.ExpandableCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExpandableCardTest {
     @get:Rule

    val composeTestRule = createComposeRule()
    @Test
    fun myComposableTest() {
        // Perform actions and assertions on the Composable
            composeTestRule.setContent {
                ExpandableCard(title = "Test", description = "Dette er en test som viser at composable-funksjonen fungerer", img = "cottonjacket")
            }
            composeTestRule.onNodeWithText("Test").assertExists()
    }
}
