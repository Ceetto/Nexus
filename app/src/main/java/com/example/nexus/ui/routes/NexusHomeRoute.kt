 package com.example.nexus.ui.routes

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.HomePageCategoryComponent
import com.example.nexus.ui.components.HomePageGameComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusHomeViewModel


 @ExperimentalComposeUiApi
@Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
     val focusManager = LocalFocusManager.current
     Scaffold(
         topBar = { NexusTopBar(navController = navController, canPop = false) }
     ) {

         Column(
             Modifier.verticalScroll(rememberScrollState())
         ){
             //Popular
             HomePageCategoryComponent(vM, vM.getPopularGames(), onOpenGameDetails, focusManager, "Popular:", vM.isSearchingPopular())

             //Top Rated
             HomePageCategoryComponent(vM, vM.getBestGames(), onOpenGameDetails, focusManager, "Top Rated:", vM.isSearchingBest())

             //Trending
             HomePageCategoryComponent(vM, vM.getTrendingGames(), onOpenGameDetails, focusManager, "Trending:", vM.isSearchingTrending())

             //Upcomming
             HomePageCategoryComponent(vM, vM.getUpcomingGames(), onOpenGameDetails, focusManager, "Upcoming Releases:", vM.isSearchingUpcoming())

             //Recommended

         }



     }


}
