package com.example.nexus

import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.nexus.ui.components.list.ListTopNavigationBar
import com.example.nexus.ui.routes.ListCategory
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

class ListTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenClickingOnTopBarShouldChangeCategory(){
        val test = mock<(ListCategory) -> Unit>()

        composeTestRule.setContent {
            ListTopNavigationBar(
                selectedCategory = ListCategory.ALL,
                onSelectedCategoryChanged = { })
        }
//        composeTestRule.onNodeWithText(ListCategory.COMPLETED.value).assertIsToggleable()
//        composeTestRule.waitForIdle()
//        verify(test, atLeastOnce())(ListCategory.COMPLETED)
    }

}