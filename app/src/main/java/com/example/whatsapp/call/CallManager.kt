package com.example.whatsapp.call

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.telecom.PhoneAccount
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    companion object {
        private const val TAG = "CallManager"
        private const val PHONE_ACCOUNT_ID = "whatsapp_call_account"
        private const val PHONE_ACCOUNT_LABEL = "WhatsApp"
    }
    
    private val telecomManager: TelecomManager by lazy {
        context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
    }
    
    private val phoneAccountHandle: PhoneAccountHandle by lazy {
        PhoneAccountHandle(
            ComponentName(context, CallConnectionService::class.java),
            PHONE_ACCOUNT_ID
        )
    }
    
    fun initializeCallManager() {
        try {
            // Create phone account for WhatsApp calls
            val phoneAccount = PhoneAccount.builder(phoneAccountHandle, PHONE_ACCOUNT_LABEL)
                .setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER)
                .setSupportedUriSchemes(listOf(PhoneAccount.SCHEME_TEL))
                .build()
            
            telecomManager.registerPhoneAccount(phoneAccount)
            Log.d(TAG, "Phone account registered successfully")
        } catch (e: SecurityException) {
            Log.e(TAG, "Failed to register phone account", e)
        }
    }
    
    fun makeCall(phoneNumber: String, isVideoCall: Boolean = false) {
        try {
            val extras = Bundle().apply {
                putBoolean(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE, isVideoCall)
            }
            
            telecomManager.placeCall(
                Uri.fromParts(PhoneAccount.SCHEME_TEL, phoneNumber, null),
                extras
            )
            
            Log.d(TAG, "Call initiated to $phoneNumber")
        } catch (e: SecurityException) {
            Log.e(TAG, "Failed to make call", e)
        }
    }
    
    fun endCall() {
        try {
            // This would typically be handled by the connection
            Log.d(TAG, "Ending call")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to end call", e)
        }
    }
    
    fun isCallInProgress(): Boolean {
        return try {
            telecomManager.isInCall
        } catch (e: SecurityException) {
            false
        }
    }
    
    fun answerCall() {
        // This is typically handled by the system UI or the connection
        Log.d(TAG, "Answering call")
    }
    
    fun rejectCall() {
        // This is typically handled by the system UI or the connection
        Log.d(TAG, "Rejecting call")
    }
}