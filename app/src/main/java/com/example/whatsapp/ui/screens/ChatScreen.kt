package com.example.whatsapp.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.whatsapp.data.models.Message
import com.example.whatsapp.data.models.MessageType
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatId: String,
    onNavigateBack: () -> Unit,
    onStartVoiceCall: (String) -> Unit,
    onStartVideoCall: (String) -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    
    // Mock messages for demonstration
    val sampleMessages = listOf(
        Message(
            id = "1",
            senderId = "user2",
            content = "Hey there! How are you?",
            timestamp = System.currentTimeMillis() - 3600000
        ),
        Message(
            id = "2",
            senderId = "user1", // Current user
            content = "I'm doing great! Thanks for asking 😊",
            timestamp = System.currentTimeMillis() - 3500000
        ),
        Message(
            id = "3",
            senderId = "user2",
            content = "That's awesome! Want to catch up over a call?",
            timestamp = System.currentTimeMillis() - 3400000
        ),
        Message(
            id = "4",
            senderId = "user1",
            content = "Sure! Video call would be perfect",
            timestamp = System.currentTimeMillis() - 3300000
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👤", style = MaterialTheme.typography.headlineSmall)
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column {
                            Text(
                                text = "Contact Name",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Online",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.8f)
                                )
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
                    IconButton(onClick = { onStartVideoCall("call_${System.currentTimeMillis()}") }) {
                        Icon(
                            Icons.Default.Videocam,
                            contentDescription = "Video Call",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { onStartVoiceCall("call_${System.currentTimeMillis()}") }) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Voice Call",
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
        },
        bottomBar = {
            ChatBottomBar(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSendMessage = {
                    if (messageText.isNotBlank()) {
                        // TODO: Send message
                        messageText = ""
                    }
                },
                onAttachFile = { /* Attach file */ },
                onRecordVoice = { /* Record voice message */ }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFE8E8E8)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleMessages) { message ->
                MessageBubble(
                    message = message,
                    isFromCurrentUser = message.senderId == "user1"
                )
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isFromCurrentUser: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFromCurrentUser) Color(0xFFDCF8C6) else Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray
                    ),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomBar(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    onAttachFile: () -> Unit,
    onRecordVoice: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageTextChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message") },
            shape = RoundedCornerShape(24.dp),
            leadingIcon = {
                IconButton(onClick = onAttachFile) {
                    Icon(
                        Icons.Default.Attachment,
                        contentDescription = "Attach",
                        tint = Color.Gray
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF075E54),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
            )
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        FloatingActionButton(
            onClick = if (messageText.isBlank()) onRecordVoice else onSendMessage,
            modifier = Modifier.size(48.dp),
            containerColor = Color(0xFF075E54)
        ) {
            Icon(
                imageVector = if (messageText.isBlank()) Icons.Default.Mic else Icons.Default.Send,
                contentDescription = if (messageText.isBlank()) "Voice Message" else "Send",
                tint = Color.White
            )
        }
    }
}