package com.example.whatsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun VideoCallScreen(
    callId: String,
    onEndCall: () -> Unit
) {
    var callDuration by remember { mutableIntStateOf(0) }
    var isConnected by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var isCameraOn by remember { mutableStateOf(true) }
    var isSpeakerOn by remember { mutableStateOf(true) } // Speaker is usually on for video calls
    var isControlsVisible by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        // Simulate call connection
        delay(2000)
        isConnected = true
        
        // Start call timer
        while (isConnected) {
            delay(1000)
            callDuration++
        }
    }
    
    LaunchedEffect(isControlsVisible) {
        if (isControlsVisible) {
            delay(5000) // Hide controls after 5 seconds
            isControlsVisible = false
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Remote video view (placeholder)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1F1F)),
            contentAlignment = Alignment.Center
        ) {
            if (!isConnected) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Connecting...",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White
                        )
                    )
                }
            } else {
                // Simulate remote video
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Remote Video",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White
                        )
                    )
                }
            }
        }
        
        // Local video view (small preview)
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(120.dp, 160.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (isCameraOn) Color.Blue else Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                if (isCameraOn) {
                    Text(
                        text = "You",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White
                        )
                    )
                } else {
                    Icon(
                        Icons.Default.VideocamOff,
                        contentDescription = "Camera Off",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        
        // Call duration
        if (isConnected && isControlsVisible) {
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.6f)
                )
            ) {
                Text(
                    text = formatCallDuration(callDuration),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        
        // Call controls
        if (isControlsVisible) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mute button
                FloatingActionButton(
                    onClick = { isMuted = !isMuted },
                    modifier = Modifier.size(56.dp),
                    containerColor = if (isMuted) Color.Red else Color.Black.copy(alpha = 0.6f)
                ) {
                    Icon(
                        if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = if (isMuted) "Unmute" else "Mute",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Camera toggle button
                FloatingActionButton(
                    onClick = { isCameraOn = !isCameraOn },
                    modifier = Modifier.size(56.dp),
                    containerColor = if (!isCameraOn) Color.Red else Color.Black.copy(alpha = 0.6f)
                ) {
                    Icon(
                        if (isCameraOn) Icons.Default.Videocam else Icons.Default.VideocamOff,
                        contentDescription = if (isCameraOn) "Turn Camera Off" else "Turn Camera On",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
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
                
                // Switch camera button
                FloatingActionButton(
                    onClick = { /* Switch camera */ },
                    modifier = Modifier.size(56.dp),
                    containerColor = Color.Black.copy(alpha = 0.6f)
                ) {
                    Icon(
                        Icons.Default.FlipCameraAndroid,
                        contentDescription = "Switch Camera",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Speaker button
                FloatingActionButton(
                    onClick = { isSpeakerOn = !isSpeakerOn },
                    modifier = Modifier.size(56.dp),
                    containerColor = if (!isSpeakerOn) Color.Gray else Color.Black.copy(alpha = 0.6f)
                ) {
                    Icon(
                        if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                        contentDescription = if (isSpeakerOn) "Speaker Off" else "Speaker On",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        
        // Contact name overlay
        if (isConnected && isControlsVisible) {
            Text(
                text = "Contact Name",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

private fun formatCallDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}