package com.example.whatsapp.data.models

data class User(
    val id: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val profileImageUrl: String = "",
    val status: String = "Hey there! I am using WhatsApp.",
    val isOnline: Boolean = false,
    val lastSeen: Long = System.currentTimeMillis()
)