package com.example.whatsapp.data.models

data class Call(
    val id: String = "",
    val callerId: String = "",
    val receiverId: String = "",
    val type: CallType = CallType.VOICE,
    val status: CallStatus = CallStatus.OUTGOING,
    val duration: Long = 0, // in seconds
    val timestamp: Long = System.currentTimeMillis(),
    val isGroupCall: Boolean = false,
    val participants: List<String> = emptyList()
)

enum class CallType {
    VOICE,
    VIDEO
}

enum class CallStatus {
    OUTGOING,
    INCOMING,
    MISSED,
    DECLINED,
    COMPLETED
}