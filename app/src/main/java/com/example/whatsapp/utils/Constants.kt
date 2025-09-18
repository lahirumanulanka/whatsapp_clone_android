package com.example.whatsapp.utils

object Constants {
    // App constants
    const val APP_NAME = "WhatsApp Clone"
    
    // Database constants
    const val DATABASE_NAME = "whatsapp_db"
    const val DATABASE_VERSION = 1
    
    // Shared preferences
    const val PREFS_NAME = "whatsapp_prefs"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_PHONE = "user_phone"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    
    // Network constants
    const val BASE_URL = "https://api.whatsapp-clone.com/"
    const val WEBSOCKET_URL = "wss://api.whatsapp-clone.com/ws/"
    
    // Call constants
    const val MAX_CALL_DURATION = 3600 // 1 hour in seconds
    const val CALL_TIMEOUT_DURATION = 30000 // 30 seconds
    
    // Media constants
    const val MAX_IMAGE_SIZE = 5 * 1024 * 1024 // 5MB
    const val MAX_VIDEO_SIZE = 50 * 1024 * 1024 // 50MB
    const val MAX_AUDIO_DURATION = 600 // 10 minutes in seconds
    
    // UI constants
    const val ANIMATION_DURATION = 300
    const val SPLASH_DELAY = 2000
    
    // Permission request codes
    const val PERMISSION_REQUEST_CAMERA = 1001
    const val PERMISSION_REQUEST_MICROPHONE = 1002
    const val PERMISSION_REQUEST_STORAGE = 1003
    const val PERMISSION_REQUEST_LOCATION = 1004
    const val PERMISSION_REQUEST_CONTACTS = 1005
    const val PERMISSION_REQUEST_PHONE = 1006
    
    // File picker request codes
    const val REQUEST_IMAGE_PICK = 2001
    const val REQUEST_VIDEO_PICK = 2002
    const val REQUEST_DOCUMENT_PICK = 2003
    const val REQUEST_CAMERA_CAPTURE = 2004
    
    // Message types
    const val MESSAGE_TYPE_TEXT = "text"
    const val MESSAGE_TYPE_IMAGE = "image"
    const val MESSAGE_TYPE_VIDEO = "video"
    const val MESSAGE_TYPE_AUDIO = "audio"
    const val MESSAGE_TYPE_DOCUMENT = "document"
    const val MESSAGE_TYPE_LOCATION = "location"
    const val MESSAGE_TYPE_CONTACT = "contact"
    
    // Call types
    const val CALL_TYPE_VOICE = "voice"
    const val CALL_TYPE_VIDEO = "video"
    
    // Status types
    const val STATUS_TYPE_TEXT = "text"
    const val STATUS_TYPE_IMAGE = "image"
    const val STATUS_TYPE_VIDEO = "video"
    
    // Notification constants
    const val NOTIFICATION_CHANNEL_MESSAGES = "messages_channel"
    const val NOTIFICATION_CHANNEL_CALLS = "calls_channel"
    const val NOTIFICATION_CHANNEL_GENERAL = "general_channel"
    
    const val NOTIFICATION_ID_MESSAGE = 3001
    const val NOTIFICATION_ID_CALL = 3002
    const val NOTIFICATION_ID_GENERAL = 3003
}