package com.example.nexus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import proto.Website

@Composable
fun LinkComponent(
    site: Website,
    getIcon : (String) -> Int?
){
    val display = site.category.name.lowercase().replace("website_", "")

    // UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

    Image(
        painter = rememberAsyncImagePainter(
            getIcon(display)
        ),
        contentDescription = "icon",
        modifier = Modifier.size(50.dp, 50.dp).padding(5.dp)
            .pointerInput(Unit){
            detectTapGestures (onTap = {
                    uriHandler.openUri(site.url)
            })
        }
    )
}