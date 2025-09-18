package com.example.whatsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun IncomingCallScreen(
    callId: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    var isRinging by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        // Simulate call timeout after 30 seconds
        delay(30000)
        if (isRinging) {
            onDecline()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF075E54)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section with caller info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            
            Text(
                text = "Incoming call",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Caller profile picture
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    style = MaterialTheme.typography.displayMedium
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Contact Name",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "WhatsApp voice call",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
        }
        
        // Bottom section with call actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Decline button
            FloatingActionButton(
                onClick = {
                    isRinging = false
                    onDecline()
                },
                modifier = Modifier.size(72.dp),
                containerColor = Color.Red
            ) {
                Icon(
                    Icons.Default.CallEnd,
                    contentDescription = "Decline",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            // Message button
            FloatingActionButton(
                onClick = { /* Send quick message */ },
                modifier = Modifier.size(56.dp),
                containerColor = Color.White.copy(alpha = 0.2f)
            ) {
                Icon(
                    Icons.Default.Message,
                    contentDescription = "Message",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // Accept button
            FloatingActionButton(
                onClick = {
                    isRinging = false
                    onAccept()
                },
                modifier = Modifier.size(72.dp),
                containerColor = Color.Green
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "Accept",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}