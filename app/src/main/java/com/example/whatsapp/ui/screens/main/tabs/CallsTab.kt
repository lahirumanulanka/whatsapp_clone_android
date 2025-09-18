package com.example.whatsapp.ui.screens.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.data.model.Call
import com.example.whatsapp.data.model.CallStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CallsTab() {
    // Sample call data
    val sampleCalls = listOf(
        Call(
            id = "1",
            participantId = "user2",
            isIncoming = false,
            isVideoCall = false,
            startTime = System.currentTimeMillis() - 3600000,
            endTime = System.currentTimeMillis() - 3300000,
            duration = 300000,
            status = CallStatus.ENDED
        ),
        Call(
            id = "2",
            participantId = "user3",
            isIncoming = true,
            isVideoCall = true,
            startTime = System.currentTimeMillis() - 7200000,
            status = CallStatus.MISSED
        ),
        Call(
            id = "3",
            participantId = "user4",
            isIncoming = false,
            isVideoCall = false,
            startTime = System.currentTimeMillis() - 86400000,
            endTime = System.currentTimeMillis() - 86100000,
            duration = 300000,
            status = CallStatus.ENDED
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(sampleCalls) { call ->
            CallItem(call = call)
        }
    }
}

@Composable
private fun CallItem(call: Call) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle call item click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Call details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Contact ${call.participantId}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Call direction icon
                Icon(
                    imageVector = when {
                        call.status == CallStatus.MISSED && call.isIncoming -> Icons.Default.CallReceived
                        call.status == CallStatus.MISSED && !call.isIncoming -> Icons.Default.CallMade
                        call.isIncoming -> Icons.Default.CallReceived
                        else -> Icons.Default.CallMade
                    },
                    contentDescription = "Call direction",
                    tint = when (call.status) {
                        CallStatus.MISSED -> Color.Red
                        else -> Color.Gray
                    },
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Text(
                    text = formatCallTime(call.startTime),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        
        // Call type icon (audio/video)
        IconButton(
            onClick = { /* Handle call back */ }
        ) {
            Icon(
                imageVector = if (call.isVideoCall) Icons.Default.Videocam else Icons.Default.Call,
                contentDescription = if (call.isVideoCall) "Video call" else "Voice call",
                tint = Color(0xFF075E54)
            )
        }
    }
}

private fun formatCallTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 86400000 -> { // Less than 24 hours
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        }
        diff < 604800000 -> { // Less than 7 days
            SimpleDateFormat("EEE HH:mm", Locale.getDefault()).format(Date(timestamp))
        }
        else -> {
            SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(timestamp))
        }
    }
}