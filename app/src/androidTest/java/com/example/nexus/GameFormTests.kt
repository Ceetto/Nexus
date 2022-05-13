package com.example.nexus

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.components.gameForm.GameSaveButton
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GameFormTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenMinutesInputIsStringShouldNotAccept(){
        val inputInvalid = mutableStateOf(false)
        val entry = ListEntry(0, "",0, 0, "", "",
            false, 0)
        composeTestRule.setContent {
            GameSaveButton(
                focusManager = LocalFocusManager.current,
                getHours = "0",
                getMinutes = "test",
                onShowErrorPopupChanged = {b: Boolean -> inputInvalid.value = b},
                setCurrentListEntryMinutes = {},
                storeListEntry = {},
                getListEntry = entry,
                onGameFormOpenChanged = {}
            )
        }

        composeTestRule.onNodeWithText("save").performClick()
        composeTestRule.waitForIdle()

        Assert.assertTrue(inputInvalid.value)
    }

    @Test
    fun whenHoursInputIsStringShouldNotAccept(){
        val inputInvalid = mutableStateOf(false)
        val entry = ListEntry(0, "",0, 0, "", "",
            false, 0)
        composeTestRule.setContent {
            GameSaveButton(
                focusManager = LocalFocusManager.current,
                getHours = "test",
                getMinutes = "0",
                onShowErrorPopupChanged = {b: Boolean -> inputInvalid.value = b},
                setCurrentListEntryMinutes = {},
                storeListEntry = {},
                getListEntry = entry,
                onGameFormOpenChanged = {}
            )
        }

        composeTestRule.onNodeWithText("save").performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(inputInvalid.value)
    }

    @Test
    fun whenMinutesInputIsDecimalShouldNotAccept(){
        val inputInvalid = mutableStateOf(false)
        val entry = ListEntry(0, "",0, 0, "", "",
            false, 0)
        composeTestRule.setContent {
            GameSaveButton(
                focusManager = LocalFocusManager.current,
                getHours = "0",
                getMinutes = "1.5",
                onShowErrorPopupChanged = {b: Boolean -> inputInvalid.value = b},
                setCurrentListEntryMinutes = {},
                storeListEntry = {},
                getListEntry = entry,
                onGameFormOpenChanged = {}
            )
        }

        composeTestRule.onNodeWithText("save").performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(inputInvalid.value)
    }

    @Test
    fun whenHoursInputIsDecimalShouldNotAccept(){
        val inputInvalid = mutableStateOf(false)
        val entry = ListEntry(0, "",0, 0, "", "",
            false, 0)
        composeTestRule.setContent {
            GameSaveButton(
                focusManager = LocalFocusManager.current,
                getHours = "1.5",
                getMinutes = "0",
                onShowErrorPopupChanged = {b: Boolean -> inputInvalid.value = b},
                setCurrentListEntryMinutes = {},
                storeListEntry = {},
                getListEntry = entry,
                onGameFormOpenChanged = {}
            )
        }

        composeTestRule.onNodeWithText("save").performClick()
        composeTestRule.waitForIdle()
        Assert.assertTrue(inputInvalid.value)
    }

    @Test
    fun whenTimeInputsAreIntegersShouldAcceptAndRedirect(){
        val inputInvalid = mutableStateOf(false)
        val gameFormOpened = mutableStateOf(true)
        val entry = ListEntry(0, "",0, 0, "", "",
            false, 0)
        composeTestRule.setContent {
            GameSaveButton(
                focusManager = LocalFocusManager.current,
                getHours = "0",
                getMinutes = "0",
                onShowErrorPopupChanged = {b: Boolean -> inputInvalid.value = b},
                setCurrentListEntryMinutes = {},
                storeListEntry = {},
                getListEntry = entry,
                onGameFormOpenChanged = {b: Boolean -> gameFormOpened.value = b}
            )
        }

        composeTestRule.onNodeWithText("save").performClick()
        composeTestRule.waitForIdle()
        Assert.assertFalse(inputInvalid.value)
        Assert.assertFalse(gameFormOpened.value)
    }
}