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
fun OutgoingCallScreen(
    callId: String,
    onEndCall: () -> Unit
) {
    var callStatus by remember { mutableStateOf("Calling...") }
    var callDuration by remember { mutableIntStateOf(0) }
    var isConnected by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var isSpeakerOn by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        // Simulate call connection after 3 seconds
        delay(3000)
        callStatus = "Connected"
        isConnected = true
        
        // Start call timer
        while (isConnected) {
            delay(1000)
            callDuration++
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF075E54)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top section with contact info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            
            Text(
                text = callStatus,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
            
            if (isConnected) {
                Text(
                    text = formatCallDuration(callDuration),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Contact profile picture
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
        
        // Bottom section with call controls
        Column(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Call control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Mute button
                FloatingActionButton(
                    onClick = { isMuted = !isMuted },
                    modifier = Modifier.size(56.dp),
                    containerColor = if (isMuted) Color.White else Color.White.copy(alpha = 0.2f)
                ) {
                    Icon(
                        if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = if (isMuted) Color(0xFF075E54) else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Speaker button
                FloatingActionButton(
                    onClick = { isSpeakerOn = !isSpeakerOn },
                    modifier = Modifier.size(56.dp),
                    containerColor = if (isSpeakerOn) Color.White else Color.White.copy(alpha = 0.2f)
                ) {
                    Icon(
                        Icons.Default.VolumeUp,
                        contentDescription = if (isSpeakerOn) "Speaker Off" else "Speaker On",
                        tint = if (isSpeakerOn) Color(0xFF075E54) else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Add contact button
                FloatingActionButton(
                    onClick = { /* Add contact to call */ },
                    modifier = Modifier.size(56.dp),
                    containerColor = Color.White.copy(alpha = 0.2f)
                ) {
                    Icon(
                        Icons.Default.PersonAdd,
                        contentDescription = "Add Contact",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // End call button
            FloatingActionButton(
                onClick = {
                    isConnected = false
                    onEndCall()
                },
                modifier = Modifier.size(72.dp),
                containerColor = Color.Red
            ) {
                Icon(
                    Icons.Default.CallEnd,
                    contentDescription = "End Call",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

private fun formatCallDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}