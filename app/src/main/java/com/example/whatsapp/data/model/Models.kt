package com.example.whatsapp.data.model

data class User(
    val id: String,
    val phoneNumber: String,
    val name: String,
    val profilePicture: String? = null,
    val status: String = "",
    val isOnline: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis()
)

data class Chat(
    val id: String,
    val participants: List<String>,
    val lastMessage: Message?,
    val lastMessageTime: Long,
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupPicture: String? = null
)

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val type: MessageType = MessageType.TEXT,
    val status: MessageStatus = MessageStatus.SENT,
    val mediaUrl: String? = null
)

enum class MessageType {
    TEXT, IMAGE, VIDEO, AUDIO, DOCUMENT
}

enum class MessageStatus {
    SENDING, SENT, DELIVERED, READ
}

data class Call(
    val id: String,
    val participantId: String,
    val isIncoming: Boolean,
    val isVideoCall: Boolean,
    val startTime: Long,
    val endTime: Long? = null,
    val duration: Long = 0,
    val status: CallStatus
)

enum class CallStatus {
    INCOMING, OUTGOING, ONGOING, ENDED, MISSED, DECLINED
}