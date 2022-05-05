package com.example.nexus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import proto.Website

@Composable
fun LinkComponent(
    site: Website
){
    val url = site.url
    val display = site.category.name.lowercase().replace("website_", "")
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        append(display)
        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline
            ), start = 0, end = display.length
        )
        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = 0,
            end = display.length
        )
    }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

// 🔥 Clickable text returns position of text that is clicked in onClick callback
    ClickableText(
        modifier = Modifier
            .fillMaxWidth(),
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}