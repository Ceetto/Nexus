package com.example.nexus.ui.routes.settings

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nexus.ui.components.NexusTopBar
import com.example.nexus.viewmodels.NexusProfileViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NexusSettingsPicture(vM: NexusProfileViewModel, navController: NavHostController){
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = { NexusTopBar(navController = navController, canPop = true, focusManager) }
    ) {
        Column() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp, 20.dp) ,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Current Picture: ")
                
                Spacer(Modifier.width(30.dp))

                Box(
                    modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.Red)
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "New Picture: ")
                Spacer(Modifier.width(40.dp))
                result.value?.let {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "new profile pic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxWidth() ,contentAlignment = Alignment.CenterEnd) {
                    Button(
                        onClick = { launcher.launch(arrayOf("image/*")); },
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.White),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "button-icon")
                    }
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){

                Button(onClick = {
                    if (result.value != null){
                        //TODO save image
                    }
                }){
                    Text("Save");
                }


            }
        }
    }

}