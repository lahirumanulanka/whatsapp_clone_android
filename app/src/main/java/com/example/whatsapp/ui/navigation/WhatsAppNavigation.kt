package com.example.whatsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whatsapp.ui.screens.chat.ChatScreen
import com.example.whatsapp.ui.screens.main.MainScreen
import com.example.whatsapp.ui.screens.splash.SplashScreen

@Composable
fun WhatsAppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToChat = { chatId ->
                    navController.navigate(Screen.Chat.createRoute(chatId))
                }
            )
        }
        
        composable(Screen.Chat.route) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(
                chatId = chatId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}