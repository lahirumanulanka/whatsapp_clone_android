package com.example.whatsapp.data.repository

import com.example.whatsapp.data.models.Chat
import com.example.whatsapp.data.models.Message
import com.example.whatsapp.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatRepository {
    
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: Flow<List<Chat>> = _chats.asStateFlow()
    
    private val _messages = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
    val messages: Flow<Map<String, List<Message>>> = _messages.asStateFlow()
    
    // Mock data initialization
    init {
        initializeMockData()
    }
    
    suspend fun getChats(): List<Chat> {
        return _chats.value
    }
    
    suspend fun getChatById(chatId: String): Chat? {
        return _chats.value.find { it.id == chatId }
    }
    
    suspend fun getMessagesForChat(chatId: String): List<Message> {
        return _messages.value[chatId] ?: emptyList()
    }
    
    suspend fun sendMessage(chatId: String, message: Message) {
        val currentMessages = _messages.value.toMutableMap()
        val chatMessages = currentMessages[chatId]?.toMutableList() ?: mutableListOf()
        chatMessages.add(message)
        currentMessages[chatId] = chatMessages
        _messages.value = currentMessages
        
        // Update chat's last message
        updateChatLastMessage(chatId, message)
    }
    
    suspend fun createChat(chat: Chat): String {
        val currentChats = _chats.value.toMutableList()
        currentChats.add(chat)
        _chats.value = currentChats
        return chat.id
    }
    
    suspend fun deleteChat(chatId: String) {
        val currentChats = _chats.value.toMutableList()
        currentChats.removeAll { it.id == chatId }
        _chats.value = currentChats
        
        val currentMessages = _messages.value.toMutableMap()
        currentMessages.remove(chatId)
        _messages.value = currentMessages
    }
    
    suspend fun markMessagesAsRead(chatId: String) {
        val currentMessages = _messages.value.toMutableMap()
        val chatMessages = currentMessages[chatId]?.map { message ->
            message.copy(isRead = true)
        }
        if (chatMessages != null) {
            currentMessages[chatId] = chatMessages
            _messages.value = currentMessages
        }
        
        // Update chat unread count
        val currentChats = _chats.value.map { chat ->
            if (chat.id == chatId) {
                chat.copy(unreadCount = 0)
            } else {
                chat
            }
        }
        _chats.value = currentChats
    }
    
    private fun updateChatLastMessage(chatId: String, message: Message) {
        val currentChats = _chats.value.map { chat ->
            if (chat.id == chatId) {
                chat.copy(
                    lastMessage = message,
                    updatedAt = message.timestamp,
                    unreadCount = if (message.senderId != getCurrentUserId()) chat.unreadCount + 1 else chat.unreadCount
                )
            } else {
                chat
            }
        }
        _chats.value = currentChats
    }
    
    private fun getCurrentUserId(): String {
        // TODO: Get from authentication
        return "user1"
    }
    
    private fun initializeMockData() {
        val mockChats = listOf(
            Chat(
                id = "chat1",
                participants = listOf("user1", "user2"),
                lastMessage = Message(
                    id = "msg1",
                    senderId = "user2",
                    receiverId = "user1",
                    chatId = "chat1",
                    content = "Hey, how are you doing?",
                    timestamp = System.currentTimeMillis() - 3600000
                ),
                unreadCount = 2
            ),
            Chat(
                id = "chat2",
                participants = listOf("user1", "user3"),
                lastMessage = Message(
                    id = "msg2",
                    senderId = "user3",
                    receiverId = "user1",
                    chatId = "chat2",
                    content = "See you tomorrow!",
                    timestamp = System.currentTimeMillis() - 7200000
                ),
                unreadCount = 0
            ),
            Chat(
                id = "chat3",
                participants = listOf("user1", "user4", "user5"),
                isGroup = true,
                groupName = "Family Group",
                lastMessage = Message(
                    id = "msg3",
                    senderId = "user4",
                    receiverId = "",
                    chatId = "chat3",
                    content = "Good morning everyone!",
                    timestamp = System.currentTimeMillis() - 14400000
                ),
                unreadCount = 5
            )
        )
        
        val mockMessages = mapOf(
            "chat1" to listOf(
                Message(
                    id = "msg1_1",
                    senderId = "user2",
                    receiverId = "user1",
                    chatId = "chat1",
                    content = "Hey there!",
                    timestamp = System.currentTimeMillis() - 7200000
                ),
                Message(
                    id = "msg1_2",
                    senderId = "user1",
                    receiverId = "user2",
                    chatId = "chat1",
                    content = "Hi! How are you?",
                    timestamp = System.currentTimeMillis() - 7000000
                ),
                Message(
                    id = "msg1_3",
                    senderId = "user2",
                    receiverId = "user1",
                    chatId = "chat1",
                    content = "I'm good! Want to have a video call?",
                    timestamp = System.currentTimeMillis() - 3600000
                )
            ),
            "chat2" to listOf(
                Message(
                    id = "msg2_1",
                    senderId = "user3",
                    receiverId = "user1",
                    chatId = "chat2",
                    content = "Don't forget about tomorrow's meeting",
                    timestamp = System.currentTimeMillis() - 8000000
                ),
                Message(
                    id = "msg2_2",
                    senderId = "user1",
                    receiverId = "user3",
                    chatId = "chat2",
                    content = "Sure, I'll be there",
                    timestamp = System.currentTimeMillis() - 7200000
                )
            )
        )
        
        _chats.value = mockChats
        _messages.value = mockMessages
    }
}