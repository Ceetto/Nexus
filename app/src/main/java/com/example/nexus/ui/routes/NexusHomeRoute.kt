 package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.HorizontalGamesListingComponent
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusHomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import proto.Game


 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
 @ExperimentalComposeUiApi
 @Composable
fun NexusHomeRoute(
    vM: NexusHomeViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit,
    isShuffling: MutableState<Boolean> = mutableStateOf(false)
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
                 HorizontalGamesListingComponent(vM.getTrendingGames(), onOpenGameDetails, focusManager,
                     "Trending:", vM.isSearchingTrending())

                 //Recommended


                 if(vM.getFavouriteGames().isNotEmpty() || vM.isSearchingFavourite()){
                     HorizontalGamesListingComponent(vM.getRecommendedGames(), onOpenGameDetails, focusManager,
                         "Recommended:", vM.isSearchingFavourite() || isShuffling.value)
                 } else {
                     Text("Recommended", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 5.dp))
                     Text("Favourite games to get recommendations", Modifier.padding(start = 5.dp))
                 }



                 //Upcomming
                 HorizontalGamesListingComponent(vM.getUpcomingGames(), onOpenGameDetails, focusManager,
                     "Upcoming Releases:", vM.isSearchingUpcoming())

                 //Top Rated
                 HorizontalGamesListingComponent(vM.getBestGames(), onOpenGameDetails, focusManager,
                     "Top Rated:", vM.isSearchingBest())

                 //Most Popular
                 HorizontalGamesListingComponent(vM.getPopularGames(), onOpenGameDetails, focusManager,
                     "Most Popular:", vM.isSearchingPopular())


             }
         }


     }


}
