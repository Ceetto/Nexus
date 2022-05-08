package com.example.nexus.ui.routes.search

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.SearchBarComponent
import com.example.nexus.ui.components.SearchResultComponent
import com.example.nexus.viewmodels.games.NexusSearchViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@Composable
fun NexusSearchRoute(
    vM: NexusSearchViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) },
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { focusManager.clearFocus()})}
    ) {
        Column (Modifier.fillMaxHeight()){
            Row{
                val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
                SearchBarComponent(onSearch = {
                        vM.setSearched(true); vM.onSearchEvent(); keyboardController?.hide()
                    },
                    vM.getSearchTerm(),
                    {s:String -> vM.setSearchTerm(s)},
                    {b: Boolean -> vM.setSearched(b)},
                    {
                        vM.setSearched(false)
                        vM.setSearchTerm("")
                        vM.emptyList()
                    }
                )
            }

            SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = vM.isRefreshing()), onRefresh = { vM.onSearchEvent() }) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxHeight()
                ) {
                    if (vM.isRefreshing()) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .height(50.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                        }

                    } else {
                        if (vM.getGameList().isEmpty()) {
                            if (vM.hasSearched()) {
                                Text("no results")
                            }
                        } else {
                            vM.getGameList().forEach { game ->
                                SearchResultComponent(
                                    game = game,
                                    onClick = onOpenGameDetails,
                                    focusManager
                                )
                            }
                            Row(Modifier.fillMaxWidth(), Arrangement.Center){
                                if(!vM.isSearching()){
                                    Button(onClick = { vM.loadMore() }) {
                                        Text("load more" )
                                    }
                                } else {

                                    CircularProgressIndicator()
                                }
                            }


                        }

                    }

                }
            }
        }
    }


}