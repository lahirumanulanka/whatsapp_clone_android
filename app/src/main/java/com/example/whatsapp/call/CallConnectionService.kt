package com.example.whatsapp.call

import android.net.Uri
import android.telecom.*
import android.util.Log

class CallConnectionService : ConnectionService() {
    
    companion object {
        private const val TAG = "CallConnectionService"
    }
    
    override fun onCreateOutgoingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Log.d(TAG, "onCreateOutgoingConnection")
        
        val connection = WhatsAppConnection()
        connection.setAddress(request?.address, TelecomManager.PRESENTATION_ALLOWED)
        connection.setCallerDisplayName("WhatsApp Call", TelecomManager.PRESENTATION_ALLOWED)
        
        // Set connection capabilities
        connection.connectionCapabilities = Connection.CAPABILITY_MUTE or
                Connection.CAPABILITY_SUPPORT_HOLD or
                Connection.CAPABILITY_HOLD
        
        // Set initial state
        connection.setDialing()
        
        return connection
    }
    
    override fun onCreateIncomingConnection(
        connectionManagerPhoneAccount: PhoneAccountHandle?,
        request: ConnectionRequest?
    ): Connection {
        Log.d(TAG, "onCreateIncomingConnection")
        
        val connection = WhatsAppConnection()
        connection.setAddress(request?.address, TelecomManager.PRESENTATION_ALLOWED)
        connection.setCallerDisplayName("WhatsApp Call", TelecomManager.PRESENTATION_ALLOWED)
        
        // Set connection capabilities
        connection.connectionCapabilities = Connection.CAPABILITY_MUTE or
                Connection.CAPABILITY_SUPPORT_HOLD or
                Connection.CAPABILITY_HOLD
        
        // Set initial state
        connection.setRinging()
        
        return connection
    }
}

class WhatsAppConnection : Connection() {
    
    companion object {
        private const val TAG = "WhatsAppConnection"
    }
    
    override fun onAnswer() {
        Log.d(TAG, "onAnswer")
        setActive()
    }
    
    override fun onReject() {
        Log.d(TAG, "onReject")
        setDisconnected(DisconnectCause(DisconnectCause.REJECTED))
        destroy()
    }
    
    override fun onDisconnect() {
        Log.d(TAG, "onDisconnect")
        setDisconnected(DisconnectCause(DisconnectCause.LOCAL))
        destroy()
    }
    
    override fun onAbort() {
        Log.d(TAG, "onAbort")
        setDisconnected(DisconnectCause(DisconnectCause.CANCELED))
        destroy()
    }
    
    override fun onHold() {
        Log.d(TAG, "onHold")
        setOnHold()
    }
    
    override fun onUnhold() {
        Log.d(TAG, "onUnhold")
        setActive()
    }
    
    override fun onPlayDtmfTone(c: Char) {
        Log.d(TAG, "onPlayDtmfTone: $c")
    }
    
    override fun onStopDtmfTone() {
        Log.d(TAG, "onStopDtmfTone")
    }
    
    override fun onStateChanged(state: Int) {
        Log.d(TAG, "onStateChanged: $state")
        super.onStateChanged(state)
    }
}