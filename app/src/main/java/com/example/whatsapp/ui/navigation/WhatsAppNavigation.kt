package com.example.whatsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whatsapp.ui.screens.MainScreen
import com.example.whatsapp.ui.screens.SplashScreen
import com.example.whatsapp.ui.screens.LoginScreen
import com.example.whatsapp.ui.screens.ChatScreen
import com.example.whatsapp.ui.screens.IncomingCallScreen
import com.example.whatsapp.ui.screens.OutgoingCallScreen
import com.example.whatsapp.ui.screens.VideoCallScreen

@Composable
fun WhatsAppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToChat = { chatId ->
                    navController.navigate(Screen.Chat.createRoute(chatId))
                },
                onNavigateToIncomingCall = { callId ->
                    navController.navigate(Screen.IncomingCall.createRoute(callId))
                },
                onNavigateToOutgoingCall = { callId ->
                    navController.navigate(Screen.OutgoingCall.createRoute(callId))
                }
            )
        }
        
        composable(Screen.Chat.route) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            ChatScreen(
                chatId = chatId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onStartVoiceCall = { callId ->
                    navController.navigate(Screen.OutgoingCall.createRoute(callId))
                },
                onStartVideoCall = { callId ->
                    navController.navigate(Screen.VideoCall.createRoute(callId))
                }
            )
        }
        
        composable(Screen.IncomingCall.route) { backStackEntry ->
            val callId = backStackEntry.arguments?.getString("callId") ?: ""
            IncomingCallScreen(
                callId = callId,
                onAccept = {
                    // Navigate to appropriate call screen
                },
                onDecline = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.OutgoingCall.route) { backStackEntry ->
            val callId = backStackEntry.arguments?.getString("callId") ?: ""
            OutgoingCallScreen(
                callId = callId,
                onEndCall = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.VideoCall.route) { backStackEntry ->
            val callId = backStackEntry.arguments?.getString("callId") ?: ""
            VideoCallScreen(
                callId = callId,
                onEndCall = {
                    navController.popBackStack()
                }
            )
        }
    }
}