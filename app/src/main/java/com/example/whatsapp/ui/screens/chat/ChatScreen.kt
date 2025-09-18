package com.example.whatsapp.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsapp.call.CallManager
import com.example.whatsapp.data.model.Message
import com.example.whatsapp.data.model.MessageType
import com.example.whatsapp.data.model.MessageStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatId: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var messageText by remember { mutableStateOf("") }
    
    // Sample messages for demonstration
    val sampleMessages = listOf(
        Message("1", chatId, "user2", "Hey, how are you?", System.currentTimeMillis() - 3600000),
        Message("2", chatId, "user1", "I'm good, thanks! How about you?", System.currentTimeMillis() - 3300000),
        Message("3", chatId, "user2", "Great! Are we still on for tomorrow?", System.currentTimeMillis() - 3000000),
        Message("4", chatId, "user1", "Yes, definitely! See you at 3 PM", System.currentTimeMillis() - 2700000),
        Message("5", chatId, "user2", "Perfect! Can't wait", System.currentTimeMillis() - 2400000)
    )
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Column {
                        Text(
                            text = "Contact $chatId",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "online",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = { 
                    // Make video call
                    viewModel.makeCall("+123456789$chatId", true)
                }) {
                    Icon(
                        Icons.Default.Videocam,
                        contentDescription = "Video call",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { 
                    // Make voice call
                    viewModel.makeCall("+123456789$chatId", false)
                }) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Voice call",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { /* More options */ }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF075E54)
            )
        )
        
        // Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFE5DDD5)) // WhatsApp chat background
                .padding(8.dp),
            reverseLayout = true
        ) {
            items(sampleMessages.reversed()) { message ->
                MessageItem(
                    message = message,
                    isCurrentUser = message.senderId == "user1"
                )
            }
        }
        
        // Message input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") },
                shape = RoundedCornerShape(25.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    if (messageText.isBlank()) {
                        Row {
                            IconButton(onClick = { /* Attach file */ }) {
                                Icon(
                                    Icons.Default.AttachFile,
                                    contentDescription = "Attach",
                                    tint = Color.Gray
                                )
                            }
                            IconButton(onClick = { /* Camera */ }) {
                                Icon(
                                    Icons.Default.CameraAlt,
                                    contentDescription = "Camera",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        // Send message
                        messageText = ""
                    }
                },
                modifier = Modifier.size(48.dp),
                containerColor = Color(0xFF075E54)
            ) {
                Icon(
                    if (messageText.isBlank()) Icons.Default.Mic else Icons.Default.Send,
                    contentDescription = if (messageText.isBlank()) "Voice message" else "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun MessageItem(
    message: Message,
    isCurrentUser: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isCurrentUser) Color(0xFFDCF8C6) else Color.White
            ),
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = if (isCurrentUser) 8.dp else 2.dp,
                bottomEnd = if (isCurrentUser) 2.dp else 8.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = message.content,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    
                    if (isCurrentUser) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = when (message.status) {
                                MessageStatus.SENT -> Icons.Default.Done
                                MessageStatus.DELIVERED -> Icons.Default.DoneAll
                                MessageStatus.READ -> Icons.Default.DoneAll
                                else -> Icons.Default.Schedule
                            },
                            contentDescription = "Message status",
                            tint = if (message.status == MessageStatus.READ) Color.Blue else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}