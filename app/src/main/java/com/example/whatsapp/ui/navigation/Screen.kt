package com.example.whatsapp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object PhoneVerification : Screen("phone_verification")
    object Main : Screen("main")
    object Chat : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
    object Call : Screen("call/{callId}") {
        fun createRoute(callId: String) = "call/$callId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}