package com.example.whatsapp.utils

import android.content.Context
import android.content.Intent
import com.example.whatsapp.data.models.Call
import com.example.whatsapp.data.models.CallType
import com.example.whatsapp.data.models.CallStatus
import com.example.whatsapp.services.CallService

class CallManager(private val context: Context) {
    
    fun startVoiceCall(contactId: String, contactName: String): String {
        val callId = generateCallId()
        val call = Call(
            id = callId,
            callerId = getCurrentUserId(),
            receiverId = contactId,
            type = CallType.VOICE
        )
        
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_START_CALL
            putExtra(CallService.EXTRA_CALL_DATA, call)
        }
        
        context.startForegroundService(intent)
        return callId
    }
    
    fun startVideoCall(contactId: String, contactName: String): String {
        val callId = generateCallId()
        val call = Call(
            id = callId,
            callerId = getCurrentUserId(),
            receiverId = contactId,
            type = CallType.VIDEO
        )
        
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_START_CALL
            putExtra(CallService.EXTRA_CALL_DATA, call)
        }
        
        context.startForegroundService(intent)
        return callId
    }
    
    fun answerCall(call: Call) {
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_ANSWER_CALL
            putExtra(CallService.EXTRA_CALL_DATA, call)
        }
        
        context.startForegroundService(intent)
    }
    
    fun endCall() {
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_END_CALL
        }
        
        context.startService(intent)
    }
    
    fun declineCall() {
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_DECLINE_CALL
        }
        
        context.startService(intent)
    }
    
    fun simulateIncomingCall(
        callerContactId: String = "test_caller", 
        callerName: String = "Test Caller",
        callType: CallType = CallType.VOICE
    ): String {
        val callId = generateCallId()
        val call = Call(
            id = callId,
            callerId = callerContactId,
            receiverId = getCurrentUserId(),
            type = callType,
            status = CallStatus.INCOMING
        )
        
        val intent = Intent(context, CallService::class.java).apply {
            action = CallService.ACTION_INCOMING_CALL
            putExtra(CallService.EXTRA_CALL_DATA, call)
            putExtra("callerName", callerName)
        }
        
        context.startForegroundService(intent)
        return callId
    }
    
    private fun generateCallId(): String {
        return "call_${System.currentTimeMillis()}"
    }
    
    private fun getCurrentUserId(): String {
        // TODO: Get actual current user ID from authentication
        return "current_user"
    }
}