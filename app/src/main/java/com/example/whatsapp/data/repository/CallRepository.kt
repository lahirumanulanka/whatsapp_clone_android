package com.example.whatsapp.data.repository

import com.example.whatsapp.data.models.Call
import com.example.whatsapp.data.models.CallStatus
import com.example.whatsapp.data.models.CallType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CallRepository {
    
    private val _callHistory = MutableStateFlow<List<Call>>(emptyList())
    val callHistory: Flow<List<Call>> = _callHistory.asStateFlow()
    
    private val _activeCall = MutableStateFlow<Call?>(null)
    val activeCall: Flow<Call?> = _activeCall.asStateFlow()
    
    init {
        initializeMockData()
    }
    
    suspend fun getCallHistory(): List<Call> {
        return _callHistory.value
    }
    
    suspend fun getCallById(callId: String): Call? {
        return _callHistory.value.find { it.id == callId }
            ?: _activeCall.value?.takeIf { it.id == callId }
    }
    
    suspend fun startCall(call: Call) {
        _activeCall.value = call
        
        // Add to history
        val currentHistory = _callHistory.value.toMutableList()
        currentHistory.add(0, call) // Add to beginning for recent calls first
        _callHistory.value = currentHistory
    }
    
    suspend fun endCall(callId: String, duration: Long) {
        _activeCall.value?.let { call ->
            if (call.id == callId) {
                val endedCall = call.copy(
                    status = CallStatus.COMPLETED,
                    duration = duration
                )
                
                // Update in history
                val currentHistory = _callHistory.value.map { historyCall ->
                    if (historyCall.id == callId) endedCall else historyCall
                }
                _callHistory.value = currentHistory
                
                _activeCall.value = null
            }
        }
    }
    
    suspend fun declineCall(callId: String) {
        _activeCall.value?.let { call ->
            if (call.id == callId) {
                val declinedCall = call.copy(status = CallStatus.DECLINED)
                
                // Update in history
                val currentHistory = _callHistory.value.map { historyCall ->
                    if (historyCall.id == callId) declinedCall else historyCall
                }
                _callHistory.value = currentHistory
                
                _activeCall.value = null
            }
        }
    }
    
    suspend fun missCall(callId: String) {
        val currentHistory = _callHistory.value.map { call ->
            if (call.id == callId) {
                call.copy(status = CallStatus.MISSED)
            } else {
                call
            }
        }
        _callHistory.value = currentHistory
    }
    
    suspend fun deleteCallFromHistory(callId: String) {
        val currentHistory = _callHistory.value.toMutableList()
        currentHistory.removeAll { it.id == callId }
        _callHistory.value = currentHistory
    }
    
    suspend fun clearCallHistory() {
        _callHistory.value = emptyList()
    }
    
    suspend fun getCallHistoryByContact(contactId: String): List<Call> {
        return _callHistory.value.filter { call ->
            call.callerId == contactId || call.receiverId == contactId
        }
    }
    
    suspend fun getRecentCalls(limit: Int = 50): List<Call> {
        return _callHistory.value.take(limit)
    }
    
    suspend fun getMissedCalls(): List<Call> {
        return _callHistory.value.filter { it.status == CallStatus.MISSED }
    }
    
    suspend fun getOutgoingCalls(): List<Call> {
        return _callHistory.value.filter { call ->
            call.status == CallStatus.OUTGOING || 
            (call.status == CallStatus.COMPLETED && call.callerId == getCurrentUserId())
        }
    }
    
    suspend fun getIncomingCalls(): List<Call> {
        return _callHistory.value.filter { call ->
            call.status == CallStatus.INCOMING || 
            (call.status == CallStatus.COMPLETED && call.receiverId == getCurrentUserId())
        }
    }
    
    suspend fun updateCallStatus(callId: String, status: CallStatus) {
        val currentHistory = _callHistory.value.map { call ->
            if (call.id == callId) {
                call.copy(status = status)
            } else {
                call
            }
        }
        _callHistory.value = currentHistory
        
        _activeCall.value?.let { call ->
            if (call.id == callId) {
                _activeCall.value = call.copy(status = status)
            }
        }
    }
    
    private fun getCurrentUserId(): String {
        // TODO: Get from authentication
        return "user1"
    }
    
    private fun initializeMockData() {
        val mockCallHistory = listOf(
            Call(
                id = "call1",
                callerId = "user2",
                receiverId = "user1",
                type = CallType.VIDEO,
                status = CallStatus.COMPLETED,
                duration = 180, // 3 minutes
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                isGroupCall = false
            ),
            Call(
                id = "call2",
                callerId = "user1",
                receiverId = "user3",
                type = CallType.VOICE,
                status = CallStatus.COMPLETED,
                duration = 420, // 7 minutes
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                isGroupCall = false
            ),
            Call(
                id = "call3",
                callerId = "user4",
                receiverId = "user1",
                type = CallType.VOICE,
                status = CallStatus.MISSED,
                duration = 0,
                timestamp = System.currentTimeMillis() - 10800000, // 3 hours ago
                isGroupCall = false
            ),
            Call(
                id = "call4",
                callerId = "user1",
                receiverId = "user5",
                type = CallType.VIDEO,
                status = CallStatus.DECLINED,
                duration = 0,
                timestamp = System.currentTimeMillis() - 14400000, // 4 hours ago
                isGroupCall = false
            ),
            Call(
                id = "call5",
                callerId = "user1",
                receiverId = "",
                type = CallType.VIDEO,
                status = CallStatus.COMPLETED,
                duration = 1200, // 20 minutes
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                isGroupCall = true,
                participants = listOf("user1", "user2", "user3", "user4")
            )
        )
        
        _callHistory.value = mockCallHistory
    }
}