package com.example.whatsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp.ui.navigation.Screen
import com.example.whatsapp.ui.navigation.WhatsAppNavigation
import com.example.whatsapp.ui.theme.WhatsappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    // Handle incoming call navigation from intent
                    LaunchedEffect(intent) {
                        val navigateTo = intent.getStringExtra("navigate_to")
                        val callId = intent.getStringExtra("call_id")
                        
                        if (navigateTo == "incoming_call" && callId != null) {
                            navController.navigate(Screen.IncomingCall.createRoute(callId))
                        }
                    }
                    
                    WhatsAppNavigation(navController = navController)
                }
            }
        }
    }
}