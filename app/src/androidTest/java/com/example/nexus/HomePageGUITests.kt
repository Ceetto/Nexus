package com.example.nexus

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.nexus.ui.components.HorizontalGameDisplayComponent
import com.example.nexus.ui.components.SearchBarComponent
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import proto.Game

class HomePageGUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenClickingSearchIconShouldExecuteSearchFunction(){
        val searched = mutableStateOf(false)
        fun search(v:Boolean){
            searched.value = v
        }
        composeTestRule.setContent {
            SearchBarComponent(
                "testing",
                onSearch = { search(true) },
                getSearchTerm = "",
                setSearchTerm = {},
                setSearched = {},
                onCancel = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("search").performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(searched.value)
    }

    @Test
    fun whenClickingCancelIconShouldExecuteCancelFunction(){
        val canceled = mutableStateOf(false)
        fun cancel(){
            canceled.value = true
        }
        composeTestRule.setContent {
            SearchBarComponent(
                "testing",
                onSearch = {},
                getSearchTerm = "not empty",
                setSearchTerm = {},
                setSearched = {},
                onCancel = {cancel()}
            )
        }
        composeTestRule.onNodeWithContentDescription("remove").performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(canceled.value)
    }

    @Test
    fun whenSettingSearchTermSearchTermShouldChange(){
        val searchTerm = mutableStateOf("")
        fun setSearchTerm(s:String){
            searchTerm.value = s
        }
        fun getSearchTerm(): String {
            return searchTerm.value
        }
        composeTestRule.setContent {
            SearchBarComponent(
                "testing",
                onSearch = {setSearchTerm("test")},
                getSearchTerm = getSearchTerm(),
                setSearchTerm = {s -> setSearchTerm(s)},
                setSearched = {},
                onCancel = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("search").performClick()
        Assert.assertEquals(getSearchTerm(), "test")
    }

    @Test
    fun whenClickingGameShouldExecuteOnClickFunction(){
        val clicked = mutableStateOf(false)
        fun onClick(){
            clicked.value = true
        }
        composeTestRule.setContent {
            HorizontalGameDisplayComponent(game = Game.getDefaultInstance(),
                onClick = {onClick()},
                focusManager = LocalFocusManager.current)
        }
        composeTestRule.onNodeWithText(Game.getDefaultInstance().name).performClick()
        Assert.assertTrue(clicked.value)
    }


}