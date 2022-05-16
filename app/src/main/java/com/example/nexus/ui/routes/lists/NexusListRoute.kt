package com.example.nexus.ui.routes.lists

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.list.ListCategoryComponent
import com.example.nexus.ui.components.list.ListTopNavigationBar
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing
import com.example.nexus.viewmodels.list.NexusListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun NexusListRoute(
    vM: NexusListViewModel,
    navController: NavHostController,
    onOpenGameDetails : (gameId: Long) -> Unit,
){
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = false, focusManager) }
    ) {
        Scaffold(topBar = {
            ListTopNavigationBar(vM.getSelectedCategory(), vM::onSelectedCategoryChanged)
        }){
            ListCategoryComponent(
                category = vM.getSelectedCategory(),
                getCategoryByName = { category -> vM.getCategoryByName(category) },
                toggleDescendingOrAscendingIcon = {vM.toggleDescendingOrAscendingIcon()},
                getDescendingOrAscendingIcon = {vM.getDescendingOrAscendingIcon()},
                getSelectedCategory = {vM.getSelectedCategory()},
                setSortOption = {s -> vM.setSortOption(s)},
                getSortOption = {vM.getSortOption()},
                onOpenGameDetails = onOpenGameDetails)
        }
    }

}

enum class ListCategory(val value: String){
    ALL("All"),
    PLAYING("Playing"),
    COMPLETED("Completed"),
    PLANNED("Planned"),
    DROPPED("Dropped"),
}

fun getAllListCategories(): List<ListCategory> =
    listOf(ListCategory.ALL, ListCategory.PLAYING, ListCategory.COMPLETED,
        ListCategory.PLANNED, ListCategory.DROPPED
    )

val ListCategoryColors = mapOf(ListCategory.PLAYING.value to Playing,
    ListCategory.COMPLETED.value to Completed,
    ListCategory.DROPPED.value to Dropped,
    ListCategory.PLANNED.value to Planned
)