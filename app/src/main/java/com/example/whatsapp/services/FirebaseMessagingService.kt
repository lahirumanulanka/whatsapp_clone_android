package com.example.whatsapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.whatsapp.MainActivity
import com.example.whatsapp.R
import com.example.whatsapp.utils.Constants

// Note: This is a placeholder for Firebase Cloud Messaging
// In a real implementation, you would extend FirebaseMessagingService
class FirebaseMessagingService : android.app.Service() {
    
    companion object {
        private const val CHANNEL_ID = "WhatsApp_Notifications"
    }
    
    override fun onBind(intent: Intent?) = null
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    // Simulated message received handler
    fun onMessageReceived(remoteMessage: Map<String, String>) {
        val title = remoteMessage["title"] ?: "New Message"
        val body = remoteMessage["body"] ?: ""
        val chatId = remoteMessage["chatId"]
        val senderId = remoteMessage["senderId"]
        
        showNotification(title, body, chatId)
    }
    
    private fun showNotification(title: String, body: String, chatId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            chatId?.let { putExtra("chatId", it) }
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // TODO: Use app icon
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .build()
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Constants.NOTIFICATION_ID_MESSAGE, notification)
    }
    
    // Simulate incoming call notification
    fun showIncomingCallNotification(callerName: String, callId: String, isVideoCall: Boolean) {
        val acceptIntent = Intent(this, CallService::class.java).apply {
            action = CallService.ACTION_ANSWER_CALL
            putExtra("callId", callId)
        }
        
        val declineIntent = Intent(this, CallService::class.java).apply {
            action = CallService.ACTION_DECLINE_CALL
            putExtra("callId", callId)
        }
        
        val acceptPendingIntent = PendingIntent.getService(
            this, 1, acceptIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val declinePendingIntent = PendingIntent.getService(
            this, 2, declineIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setContentTitle("Incoming ${if (isVideoCall) "video" else "voice"} call")
            .setContentText(callerName)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setFullScreenIntent(acceptPendingIntent, true)
            .addAction(
                android.R.drawable.ic_menu_call,
                "Accept",
                acceptPendingIntent
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Decline",
                declinePendingIntent
            )
            .setOngoing(true)
            .setSound(android.provider.Settings.System.DEFAULT_RINGTONE_URI)
            .setVibrate(longArrayOf(0, 1000, 500, 1000, 500, 1000))
            .build()
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Constants.NOTIFICATION_ID_CALL, notification)
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "WhatsApp Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for messages and calls"
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                setSound(
                    android.provider.Settings.System.DEFAULT_NOTIFICATION_URI,
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}