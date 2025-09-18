package com.example.whatsapp.ui.screens

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.whatsapp.data.models.Call
import com.example.whatsapp.data.models.CallStatus
import com.example.whatsapp.data.models.CallType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CallsTab(
    onNavigateToIncomingCall: (String) -> Unit,
    onNavigateToOutgoingCall: (String) -> Unit
) {
    // Mock call history data
    val sampleCalls = listOf(
        Call(
            id = "1",
            callerId = "user1",
            receiverId = "user2",
            type = CallType.VIDEO,
            status = CallStatus.COMPLETED,
            duration = 180,
            timestamp = System.currentTimeMillis() - 3600000
        ),
        Call(
            id = "2",
            callerId = "user2",
            receiverId = "user1",
            type = CallType.VOICE,
            status = CallStatus.MISSED,
            duration = 0,
            timestamp = System.currentTimeMillis() - 7200000
        ),
        Call(
            id = "3",
            callerId = "user1",
            receiverId = "user3",
            type = CallType.VOICE,
            status = CallStatus.COMPLETED,
            duration = 300,
            timestamp = System.currentTimeMillis() - 14400000
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(sampleCalls) { call ->
            CallItem(
                call = call,
                onCallBack = {
                    if (call.type == CallType.VIDEO) {
                        onNavigateToOutgoingCall(call.id)
                    } else {
                        onNavigateToOutgoingCall(call.id)
                    }
                }
            )
        }
    }
}

@Composable
fun CallItem(
    call: Call,
    onCallBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF075E54)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "👤",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Contact Name", // TODO: Get actual contact name
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = getCallStatusIcon(call.status),
                    contentDescription = null,
                    tint = getCallStatusColor(call.status),
                    modifier = Modifier.size(16.dp)
                )
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Text(
                    text = formatCallTime(call.timestamp, call.duration, call.status),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                )
            }
        }
        
        IconButton(
            onClick = onCallBack
        ) {
            Icon(
                imageVector = if (call.type == CallType.VIDEO) Icons.Default.Videocam else Icons.Default.Call,
                contentDescription = "Call back",
                tint = Color(0xFF075E54)
            )
        }
    }
}

private fun getCallStatusIcon(status: CallStatus): ImageVector {
    return when (status) {
        CallStatus.OUTGOING -> Icons.Default.CallMade
        CallStatus.INCOMING -> Icons.Default.CallReceived
        CallStatus.MISSED -> Icons.Default.CallReceived
        CallStatus.DECLINED -> Icons.Default.CallEnd
        CallStatus.COMPLETED -> Icons.Default.CallMade
    }
}

private fun getCallStatusColor(status: CallStatus): Color {
    return when (status) {
        CallStatus.MISSED -> Color.Red
        CallStatus.DECLINED -> Color.Red
        else -> Color.Gray
    }
}

private fun formatCallTime(timestamp: Long, duration: Long, status: CallStatus): String {
    val timeStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
    
    return if (status == CallStatus.COMPLETED && duration > 0) {
        val minutes = duration / 60
        val seconds = duration % 60
        "$timeStr (${minutes}:${seconds.toString().padStart(2, '0')})"
    } else {
        timeStr
    }
}