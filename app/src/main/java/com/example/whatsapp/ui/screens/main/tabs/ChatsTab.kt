package com.example.whatsapp.ui.screens.main.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.data.model.Chat
import com.example.whatsapp.data.model.Message
import com.example.whatsapp.data.model.MessageType
import com.example.whatsapp.data.model.MessageStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatsTab(
    onNavigateToChat: (String) -> Unit
) {
    // Sample data for demonstration
    val sampleChats = listOf(
        Chat(
            id = "1",
            participants = listOf("user1", "user2"),
            lastMessage = Message("msg1", "1", "user2", "Hey, how are you?", System.currentTimeMillis() - 3600000),
            lastMessageTime = System.currentTimeMillis() - 3600000,
            unreadCount = 2
        ),
        Chat(
            id = "2",
            participants = listOf("user1", "user3"),
            lastMessage = Message("msg2", "2", "user1", "Thanks for the help!", System.currentTimeMillis() - 7200000),
            lastMessageTime = System.currentTimeMillis() - 7200000,
            unreadCount = 0
        ),
        Chat(
            id = "3",
            participants = listOf("user1", "user4"),
            lastMessage = Message("msg3", "3", "user4", "See you tomorrow", System.currentTimeMillis() - 86400000),
            lastMessageTime = System.currentTimeMillis() - 86400000,
            unreadCount = 1
        )
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(sampleChats) { chat ->
            ChatItem(
                chat = chat,
                onClick = { onNavigateToChat(chat.id) }
            )
        }
    }
}

@Composable
private fun ChatItem(
    chat: Chat,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture placeholder
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
        
        // Chat content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (chat.isGroup) chat.groupName ?: "Group" else "Contact ${chat.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = formatTime(chat.lastMessageTime),
                    fontSize = 12.sp,
                    color = if (chat.unreadCount > 0) Color(0xFF25D366) else Color.Gray
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.lastMessage?.content ?: "No messages",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (chat.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF25D366)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chat.unreadCount.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 86400000 -> { // Less than 24 hours
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp))
        }
        diff < 604800000 -> { // Less than 7 days
            SimpleDateFormat("EEE", Locale.getDefault()).format(Date(timestamp))
        }
        else -> {
            SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date(timestamp))
        }
    }
}