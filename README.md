# WhatsApp Clone Android with CallKit Implementation

A fully functional WhatsApp clone for Android with integrated CallKit functionality, built using Jetpack Compose and modern Android development practices.

## Features

### 📱 Core WhatsApp Features
- **Splash Screen**: Branded splash screen with WhatsApp styling
- **Tabbed Interface**: Main screen with Chats, Status, and Calls tabs
- **Chat Functionality**: 
  - Real-time message display with bubble design
  - Message status indicators (sent, delivered, read)
  - Chat list with unread message counts
  - Typing indicator and online status
- **Status/Stories**: View and manage status updates
- **Call History**: Complete call log with missed/received/made call indicators

### 📞 CallKit Integration
- **Native Android Calling**: Full integration with Android's TelecomManager
- **Call Connection Service**: Custom ConnectionService for handling calls
- **Phone Account Registration**: Proper phone account setup for WhatsApp calls
- **Call Management**: 
  - Outgoing calls (voice and video)
  - Incoming call handling
  - Call state management (ringing, active, on hold)
  - Call rejection and disconnection
- **Permissions**: All necessary call-related permissions configured

### 🎨 UI/UX Design
- **WhatsApp Color Scheme**: Authentic WhatsApp green theme
- **Material Design 3**: Modern Android design patterns
- **Responsive Layout**: Optimized for different screen sizes
- **Custom Components**: Message bubbles, call items, status rings

### 🏗️ Architecture
- **MVVM Pattern**: Clean separation of concerns
- **Hilt Dependency Injection**: Modular and testable code
- **Jetpack Compose**: Modern declarative UI
- **Navigation Component**: Type-safe navigation between screens
- **Modular Structure**: Organized code with clear separation

## Project Structure

```
app/src/main/java/com/example/whatsapp/
├── call/                          # CallKit implementation
│   ├── CallConnectionService.kt   # Android Telecom integration
│   └── CallManager.kt            # Call management logic
├── data/model/                   # Data models
│   └── Models.kt                # Core data classes
├── ui/
│   ├── navigation/              # Navigation setup
│   ├── screens/
│   │   ├── splash/             # Splash screen
│   │   ├── main/               # Main tabbed interface
│   │   │   └── tabs/          # Individual tab screens
│   │   └── chat/              # Chat functionality
│   └── theme/                  # UI theme and colors
├── MainActivity.kt             # Main activity
└── WhatsAppApplication.kt      # Hilt application class
```

## Key Components

### CallKit Implementation

#### 1. CallConnectionService
- Extends Android's `ConnectionService`
- Handles incoming and outgoing call creation
- Manages call states and capabilities
- Integrates with system UI for native call experience

#### 2. CallManager
- Singleton service for call management
- Registers phone account with TelecomManager
- Provides API for making calls from the app
- Handles call permissions and security

#### 3. Call Integration in UI
- Call buttons in chat headers for quick calling
- Call history tab with call-back functionality
- Separate voice and video call options
- Call status indicators and timestamps

### Navigation Structure
- Splash screen with auto-navigation
- Main screen with bottom tab navigation
- Chat screen with back navigation
- Type-safe navigation arguments

### Data Models
- **User**: User profile information
- **Chat**: Chat metadata and participants
- **Message**: Individual message data with status
- **Call**: Call history and metadata

## Permissions Required

The app requests the following permissions for full functionality:

- `INTERNET` - Network access
- `READ_CONTACTS` / `WRITE_CONTACTS` - Contact management
- `CALL_PHONE` - Making phone calls
- `MANAGE_OWN_CALLS` - Call management
- `RECORD_AUDIO` - Voice calls and messages
- `CAMERA` - Video calls and media
- `BIND_TELECOM_CONNECTION_SERVICE` - CallKit integration
- `FOREGROUND_SERVICE` - Background call handling

## Technology Stack

- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern Android UI toolkit
- **Hilt** - Dependency injection framework
- **Navigation Compose** - Type-safe navigation
- **Material Design 3** - UI design system
- **Android Telecom Framework** - Native calling integration
- **Coroutines** - Asynchronous programming

## Setup and Installation

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on Android device (API 24+)
5. Grant necessary permissions when prompted

## CallKit Features

### Making Calls
- Tap call icons in chat headers
- Use call history to call back contacts
- Support for both voice and video calls
- Integration with system dialer

### Call Management
- Answer/reject incoming calls
- Hold/unhold during calls
- Mute/unmute functionality
- End call capabilities

### System Integration
- Calls appear in system call history
- Native Android call UI
- Background call handling
- Notification support

## Future Enhancements

- Voice message recording
- Image and file sharing
- Group chat functionality
- Push notifications
- Real-time messaging with WebSocket
- Contact synchronization
- Settings and preferences
- Dark mode support
- Message encryption

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes and demonstrates modern Android development with CallKit integration.