package com.example.whatsapp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object Chat : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object CallHistory : Screen("call_history")
    object Contacts : Screen("contacts")
    object NewChat : Screen("new_chat")
    object NewGroup : Screen("new_group")
    object IncomingCall : Screen("incoming_call/{callId}") {
        fun createRoute(callId: String) = "incoming_call/$callId"
    }
    object OutgoingCall : Screen("outgoing_call/{callId}") {
        fun createRoute(callId: String) = "outgoing_call/$callId"
    }
    object VideoCall : Screen("video_call/{callId}") {
        fun createRoute(callId: String) = "video_call/$callId"
    }
}