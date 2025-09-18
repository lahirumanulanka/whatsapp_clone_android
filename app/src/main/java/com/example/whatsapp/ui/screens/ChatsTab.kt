package com.example.whatsapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.whatsapp.data.models.Chat
import com.example.whatsapp.data.models.Message
import com.example.whatsapp.data.models.MessageType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatsTab(
    onNavigateToChat: (String) -> Unit
) {
    // Mock data for demonstration
    val sampleChats = listOf(
        Chat(
            id = "1",
            participants = listOf("user1", "user2"),
            lastMessage = Message(
                content = "Hey, how are you doing?",
                timestamp = System.currentTimeMillis() - 3600000
            ),
            unreadCount = 2
        ),
        Chat(
            id = "2", 
            participants = listOf("user1", "user3"),
            lastMessage = Message(
                content = "See you tomorrow!",
                timestamp = System.currentTimeMillis() - 7200000
            ),
            unreadCount = 0
        ),
        Chat(
            id = "3",
            participants = listOf("user1", "user4"),
            isGroup = true,
            groupName = "Family Group",
            lastMessage = Message(
                content = "Good morning everyone!",
                timestamp = System.currentTimeMillis() - 14400000
            ),
            unreadCount = 5
        )
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(sampleChats) { chat ->
                ChatItem(
                    chat = chat,
                    onClick = { onNavigateToChat(chat.id) }
                )
            }
        }
        
        FloatingActionButton(
            onClick = { /* Navigate to new chat */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF075E54)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "New Chat",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture placeholder
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF075E54)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (chat.isGroup) "👥" else "👤",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.groupName ?: "Contact Name",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = chat.lastMessage?.content ?: "No messages yet",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = formatTime(chat.lastMessage?.timestamp ?: 0),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                )
            )
            
            if (chat.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Badge(
                    containerColor = Color(0xFF25D366)
                ) {
                    Text(
                        text = chat.unreadCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

private fun formatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "Now"
        diff < 3600000 -> "${diff / 60000}m"
        diff < 86400000 -> SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        else -> SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date(timestamp))
    }
}