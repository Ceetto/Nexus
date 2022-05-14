package com.example.nexus.ui.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nexus.R;
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.ui.components.profileStats.ProfileStats
import com.example.nexus.viewmodels.profile.NexusProfileViewModel
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusProfileRoute(
    vM: NexusProfileViewModel,
    navController: NavHostController,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    vM.setUserid("")
    ProfileScreen({vM.getUser()},
        onOpenGameDetails = onOpenGameDetails,
        navController = navController,
        getCategoryByName = {s: String -> vM.getCategoryByName(s)},
        getFavourites = {vM.getFavourites()}
    )
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun InitProfile(vM: NexusProfileViewModel,
//                navController: NavHostController,
//                onOpenGameDetails: (gameId: Long) -> Unit){
//    val focusManager = LocalFocusManager.current
//
//    Scaffold(
//        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
//    ) {
//        ProfileScreen(
//            vM, onOpenGameDetails
//        )
//    }
//}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    getUser : () -> User,
    onOpenGameDetails: (gameId: Long) -> Unit,
    navController: NavHostController,
    getCategoryByName : (String) -> StateFlow<List<ListEntry>>,
    getFavourites: () -> StateFlow<List<ListEntry>>,
){
    val background = getUser().profileBackground
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)

            ) {
                if (background != "") {
                    Image(
                        painter = rememberAsyncImagePainter(background),
                        contentDescription = "profile-background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(200.dp).fillMaxWidth()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RectangleShape)
                            .background(Color.LightGray)
                    )
                }
            }
            ProfilePicture(onOpenGameDetails, getUser, getCategoryByName, getFavourites)

        }
    }

}


@Composable
fun ProfilePicture(
//    vM: NexusProfileViewModel,
    onOpenGameDetails: (gameId: Long) -> Unit,
    getUser : () -> User,
    getCategoryByName : (String) -> StateFlow<List<ListEntry>>,
    getFavourites: () -> StateFlow<List<ListEntry>>,
){
    val profilePic = getUser().profilePicture
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
            if (profilePic != ""){
            Image(
                painter = rememberAsyncImagePainter(profilePic),
                contentDescription = "profile_picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(R.mipmap.ic_launcher_round),
                    contentDescription = "profile_picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

            }

            Text(text = getUser().username, modifier = Modifier.padding(10.dp))

            ProfileStats(getCategoryByName)
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
            horizontalAlignment = Alignment.Start) {
            FavoriteList(getFavourites, onOpenGameDetails)
        }
    }
}

@Composable
fun FavoriteList(
    getFavourites: () -> StateFlow<List<ListEntry>>,
    onOpenGameDetails: (gameId: Long) -> Unit
){
    val favorites by getFavourites().collectAsState();
    Column(
        Modifier.padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
    ){
        Text("Favourites:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(Modifier.horizontalScroll(rememberScrollState())){
                favorites.forEach { entry -> Row{
                    FavoriteListComponent(entry, onOpenGameDetails, LocalFocusManager.current)
                    }
                }
            }
    }
}

@Composable
fun FavoriteListComponent(entry: ListEntry, onOpenGameDetails: (gameId: Long) -> Unit, focusManager: FocusManager) {
    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                onOpenGameDetails(entry.gameId)
                focusManager.clearFocus()
            })
        }
        .width(127.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(entry.coverUrl),
            contentDescription = "cover image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(127.dp, 150.dp)
                .padding(end = 5.dp)
        )
        Text(entry.title)
    }
}

