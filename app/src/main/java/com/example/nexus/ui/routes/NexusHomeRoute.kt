 package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.HomePageCategoryComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusHomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
         SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = vM.isRefreshing()), onRefresh = { vM.fetchGames() }) {
             Column(
                 Modifier.verticalScroll(rememberScrollState())
             ){
                 //Trending
                 HomePageCategoryComponent(vM.getTrendingGames(), onOpenGameDetails, focusManager,
                     "Trending:", vM.isSearchingTrending())

                 //Upcomming
                 HomePageCategoryComponent(vM.getUpcomingGames(), onOpenGameDetails, focusManager,
                     "Upcoming Releases:", vM.isSearchingUpcoming())

                 //Top Rated
                 HomePageCategoryComponent(vM.getBestGames(), onOpenGameDetails, focusManager,
                     "Top Rated:", vM.isSearchingBest())

                 //Most Popular
                 HomePageCategoryComponent(vM.getPopularGames(), onOpenGameDetails, focusManager,
                     "Most Popular:", vM.isSearchingPopular())

                 //Recommended

             }
         }


     }


}
