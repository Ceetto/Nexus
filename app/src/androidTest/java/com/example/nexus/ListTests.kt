package com.example.nexus

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.nexus.ui.components.list.ListTopNavigationBar
import com.example.nexus.ui.routes.ListCategory
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class ListTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenClickingOnTopBarCategoryCallsCallback(){
        //de functie mocken op deze manier geeft de volgende error:
        //Attempt to invoke interface method 'boolean org.mockito.plugins.MockMaker$TypeMockability.mockable()' on a null object reference
        //dus om te testen of de functie wordt opgeroepen wordt gebruikt gemaakt van een boolean
        //val test = mock<(ListCategory) -> Unit>()
        val test = mutableStateOf(false)

        composeTestRule.setContent {
            ListTopNavigationBar(
                selectedCategory = ListCategory.ALL,
                onSelectedCategoryChanged = { test.value = true })
        }
        composeTestRule.onNodeWithText(ListCategory.COMPLETED.value).assertIsToggleable()
        composeTestRule.onNodeWithText(ListCategory.COMPLETED.value).performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(test.value)
    }

}