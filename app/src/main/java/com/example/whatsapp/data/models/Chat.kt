package com.example.whatsapp.data.models

data class Chat(
    val id: String = "",
    val participants: List<String> = emptyList(),
    val isGroup: Boolean = false,
    val groupName: String? = null,
    val groupImageUrl: String? = null,
    val lastMessage: Message? = null,
    val unreadCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val isMuted: Boolean = false
)