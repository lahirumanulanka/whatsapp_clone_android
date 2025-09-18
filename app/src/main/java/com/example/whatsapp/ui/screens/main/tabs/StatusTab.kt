package com.example.whatsapp.ui.screens.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatusTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // My Status
        item {
            StatusItem(
                name = "My Status",
                subtitle = "Tap to add status update",
                isMyStatus = true
            )
        }
        
        // Recent updates
        item {
            Text(
                text = "Recent updates",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp, 8.dp)
            )
        }
        
        // Sample status updates
        val statusUpdates = listOf(
            "Contact 1" to "2 minutes ago",
            "Contact 2" to "5 minutes ago",
            "Contact 3" to "1 hour ago"
        )
        
        items(statusUpdates) { (name, time) ->
            StatusItem(
                name = name,
                subtitle = time,
                isMyStatus = false
            )
        }
    }
}

@Composable
private fun StatusItem(
    name: String,
    subtitle: String,
    isMyStatus: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status ring
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    if (isMyStatus) Color.Gray else Color(0xFF25D366)
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(2.dp))
            
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}