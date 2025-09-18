package com.example.whatsapp.ui.screens.chat

import androidx.lifecycle.ViewModel
import com.example.whatsapp.call.CallManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val callManager: CallManager
) : ViewModel() {
    
    fun makeCall(phoneNumber: String, isVideoCall: Boolean) {
        callManager.makeCall(phoneNumber, isVideoCall)
    }
}