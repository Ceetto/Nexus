package com.example.nexus

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.nexus.ui.components.gameDetails.GameDetailComponent
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import proto.Game

class GameDetailsTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun whenGameNotInListFavoriteIconShouldNotAppear(){
        composeTestRule.setContent { 
            GameDetailComponent(
                game = Game.getDefaultInstance(),
                found = false,
                onOpenGameDetails = {},
                focusManager = LocalFocusManager.current,
                isRefreshing = false,
                onGetGameEvent = { /*TODO*/ },
                onGameFormOpenChanged = {},
                getEditOrAddGames = "",
                onFavourite = { /*TODO*/ },
                getIcon = Icons.Default.Star,
                getLinkIcon = {0}
            )
        }

        composeTestRule.onNodeWithContentDescription("favorite").assertDoesNotExist()
    }

    @Test
    fun whenClickingFavoriteIconShouldToggleIcon(){
        val favorited = mutableStateOf(false)
        val favoriteIcon = mutableStateOf(Icons.Outlined.StarBorder)
        composeTestRule.setContent {
            GameDetailComponent(
                game = Game.getDefaultInstance(),
                found = true,
                onOpenGameDetails = {},
                focusManager = LocalFocusManager.current,
                isRefreshing = false,
                onGetGameEvent = { /*TODO*/ },
                onGameFormOpenChanged = {},
                getEditOrAddGames = "",
                onFavourite = { favorited.value = !favorited.value
                                if (favorited.value){
                                    favoriteIcon.value = Icons.Outlined.Star
                                } else {
                                    favoriteIcon.value = Icons.Outlined.StarBorder
                                }},
                getIcon = favoriteIcon.value,
                getLinkIcon = {0}
            )
        }

        composeTestRule.onNodeWithContentDescription("favorite").assertExists()
        composeTestRule.onNodeWithContentDescription("favorite").performClick()
        Assert.assertEquals(favorited.value, true)
        Assert.assertEquals(favoriteIcon.value, Icons.Outlined.Star)
        composeTestRule.onNodeWithContentDescription("favorite").performClick()
        Assert.assertEquals(favorited.value, false)
        Assert.assertEquals(favoriteIcon.value, Icons.Outlined.StarBorder)
    }
    
}