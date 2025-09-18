# WhatsApp Clone Android

A complete WhatsApp clone for Android with full CallKit implementation for voice and video calling functionality.

## Features

### ✅ Implemented Features

#### 🎨 User Interface
- **Splash Screen** - WhatsApp branded loading screen
- **Login Screen** - Phone number authentication interface
- **Main Screen** - Bottom navigation with Chats, Status, and Calls tabs
- **Chat Interface** - Message bubbles, input field, and media sharing options
- **Call Screens** - Incoming, outgoing, and video call interfaces
- **Profile Screen** - User profile management
- **Settings Screen** - Comprehensive app settings

#### 📱 Core Functionality
- **Navigation** - Seamless navigation between screens using Jetpack Navigation Compose
- **WhatsApp UI/UX** - Authentic WhatsApp look and feel with proper color scheme
- **Message System** - Text messaging with chat bubbles and timestamps
- **Call Management** - Full call lifecycle management with WebRTC integration

#### 🔊 CallKit Implementation
- **Voice Calls** - WebRTC-based voice calling with proper controls
- **Video Calls** - Full video calling with camera switching and controls
- **Call Service** - Background service for managing ongoing calls
- **Call Notifications** - System notifications for incoming calls
- **Call History** - Complete call log with duration and status tracking

#### 🏗️ Architecture
- **Clean Architecture** - Separation of concerns with UI, data, and service layers
- **Repository Pattern** - Data management with mock repositories
- **MVVM Pattern** - Modern Android architecture with Compose
- **Material Design 3** - Latest Material Design components

#### 🔐 Permissions & Security
- **Runtime Permissions** - Proper permission handling for camera, microphone, storage
- **Privacy Focused** - Comprehensive permission system for all features
- **Secure Communication** - WebRTC for secure peer-to-peer communication

### 🚧 Planned Features

#### 📷 Media Features
- [ ] Camera integration for taking photos/videos
- [ ] Image and video sharing
- [ ] Voice message recording
- [ ] Document sharing
- [ ] Location sharing

#### 👥 Social Features
- [ ] Contact synchronization
- [ ] Group chat creation and management
- [ ] Status/Stories feature
- [ ] Contact profile viewing

#### 🔔 Notifications & Real-time
- [ ] Push notifications via Firebase
- [ ] Real-time messaging with WebSocket
- [ ] Message delivery status (sent, delivered, read)
- [ ] Typing indicators

#### 🔒 Security & Privacy
- [ ] End-to-end encryption
- [ ] Message backup and restore
- [ ] Disappearing messages
- [ ] Blocked contacts management

#### ⚙️ Advanced Features
- [ ] Dark mode support
- [ ] Custom notification tones
- [ ] Chat backup to cloud storage
- [ ] Multi-language support

## Technical Stack

### 🛠️ Technologies Used
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository Pattern
- **Navigation**: Jetpack Navigation Compose
- **Calling**: WebRTC (Google WebRTC)
- **Material Design**: Material Design 3
- **Permissions**: Accompanist Permissions
- **Media**: CameraX, ExoPlayer
- **Networking**: Retrofit, OkHttp (planned)
- **State Management**: Compose State, Flow

### 📱 Android Features
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Permissions**: Camera, Microphone, Storage, Phone, Contacts, Location
- **Services**: Foreground Service for calls
- **Notifications**: System notifications for calls and messages

## Project Structure

```
app/src/main/java/com/example/whatsapp/
├── data/
│   ├── models/          # Data models (User, Message, Chat, Call)
│   └── repository/      # Data repositories with mock data
├── services/            # Background services (CallService, MessagingService)
├── ui/
│   ├── components/      # Reusable UI components
│   ├── navigation/      # Navigation setup and routes
│   ├── screens/         # All app screens
│   └── theme/           # App theming and colors
└── utils/               # Utility classes and helpers
```

## Setup and Installation

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+
- Kotlin 1.9.0+

### Installation Steps
1. Clone the repository
```bash
git clone https://github.com/lahirumanulanka/whatsapp_clone_android.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the app on a device or emulator

### Required Permissions
The app requires the following permissions for full functionality:
- **Camera** - For video calls and photo sharing
- **Microphone** - For voice and video calls
- **Storage** - For media sharing and backup
- **Phone** - For call integration
- **Contacts** - For contact synchronization
- **Location** - For location sharing
- **Notifications** - For push notifications

## Architecture Overview

### 🏗️ Clean Architecture Layers

1. **UI Layer (Presentation)**
   - Jetpack Compose screens and components
   - Navigation handling
   - State management

2. **Domain Layer (Business Logic)**
   - Use cases and business rules
   - Data models and entities

3. **Data Layer**
   - Repositories for data access
   - API clients and local storage
   - Mock data providers

### 🔄 Data Flow
1. UI triggers actions through ViewModels
2. ViewModels call repository methods
3. Repositories handle data operations
4. UI observes state changes via Flow/StateFlow

## Call Implementation Details

### 🔊 WebRTC Integration
- **PeerConnectionFactory** - WebRTC connection management
- **MediaStream** - Audio/video stream handling
- **ICE Servers** - STUN server configuration for NAT traversal
- **SDP Negotiation** - Session description protocol for call setup

### 📞 Call Flow
1. **Initiate Call** - Create PeerConnection and local media streams
2. **Signaling** - Exchange SDP offers/answers (simulated)
3. **ICE Gathering** - Collect network candidates
4. **Media Flow** - Establish peer-to-peer media connection
5. **Call Controls** - Mute, camera toggle, end call functionality

## Contributing

### 🤝 How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

### 📝 Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions small and focused

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **WhatsApp** - For the inspiration and UI/UX design
- **Google WebRTC** - For the calling infrastructure
- **Jetpack Compose** - For the modern UI framework
- **Material Design** - For the design system

## Contact

For questions or support, please contact:
- **Developer**: Lahiru Manulanka
- **GitHub**: [@lahirumanulanka](https://github.com/lahirumanulanka)

---

**Note**: This is a clone project for educational purposes. WhatsApp is a trademark of Meta Platforms, Inc.