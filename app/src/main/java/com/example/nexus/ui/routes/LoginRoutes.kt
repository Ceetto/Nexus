package com.example.nexus.ui.routes

sealed class LoginRoutes(val route: String) {
    object Login : LoginRoutes("Login")

    object Register : LoginRoutes("Register")
}