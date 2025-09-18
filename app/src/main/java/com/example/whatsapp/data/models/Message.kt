package com.example.whatsapp.data.models

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val chatId: String = "",
    val content: String = "",
    val type: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val isDelivered: Boolean = false,
    val mediaUrl: String? = null,
    val fileName: String? = null,
    val replyToMessage: String? = null
)

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    DOCUMENT,
    VOICE_NOTE,
    LOCATION
}