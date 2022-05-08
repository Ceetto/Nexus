package com.example.nexus.ui.components.loginComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton(
    getIsEmailValid: () -> Boolean,
    getIsPasswordValid: () -> Boolean,
    signIn: () -> Unit,
    checkEmail: () -> Unit
) {
    OutlinedButton(onClick = {
        checkEmail()
        signIn()
    }, content = {
        Text("Login")
    }, enabled = getIsPasswordValid() && getIsEmailValid()
        , modifier = Modifier
            .height(40.dp)
            .width(225.dp)
        , shape = RoundedCornerShape(50)
        , colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xffa5dee5))
        , border = BorderStroke(1.dp, Color(0xffa5dee5))
    )
}