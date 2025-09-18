package com.example.whatsapp.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.whatsapp.MainActivity
import com.example.whatsapp.R
import com.example.whatsapp.data.models.Call
import com.example.whatsapp.data.models.CallType
import org.webrtc.*

class CallService : Service() {
    
    companion object {
        const val CHANNEL_ID = "CallServiceChannel"
        const val NOTIFICATION_ID = 1
        
        const val ACTION_START_CALL = "START_CALL"
        const val ACTION_END_CALL = "END_CALL"
        const val ACTION_ANSWER_CALL = "ANSWER_CALL"
        const val ACTION_DECLINE_CALL = "DECLINE_CALL"
        
        const val EXTRA_CALL_DATA = "call_data"
    }
    
    private var peerConnectionFactory: PeerConnectionFactory? = null
    private var peerConnection: PeerConnection? = null
    private var localVideoTrack: VideoTrack? = null
    private var localAudioTrack: AudioTrack? = null
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        initializeWebRTC()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_CALL -> {
                val call = intent.getSerializableExtra(EXTRA_CALL_DATA) as? Call
                call?.let { startCall(it) }
            }
            ACTION_END_CALL -> {
                endCall()
            }
            ACTION_ANSWER_CALL -> {
                val call = intent.getSerializableExtra(EXTRA_CALL_DATA) as? Call
                call?.let { answerCall(it) }
            }
            ACTION_DECLINE_CALL -> {
                declineCall()
            }
        }
        
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Call Service",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for ongoing calls"
                setSound(null, null)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun initializeWebRTC() {
        val initializationOptions = PeerConnectionFactory.InitializationOptions.builder(this)
            .setEnableInternalTracer(true)
            .createInitializationOptions()
        
        PeerConnectionFactory.initialize(initializationOptions)
        
        val options = PeerConnectionFactory.Options()
        peerConnectionFactory = PeerConnectionFactory.builder()
            .setOptions(options)
            .createPeerConnectionFactory()
    }
    
    private fun startCall(call: Call) {
        val notification = createCallNotification("Outgoing call", "Calling...")
        startForeground(NOTIFICATION_ID, notification)
        
        setupPeerConnection()
        
        if (call.type == CallType.VIDEO) {
            setupLocalVideo()
        }
        setupLocalAudio()
        
        // Create offer and start call signaling
        createOffer()
    }
    
    private fun answerCall(call: Call) {
        val notification = createCallNotification("Incoming call", "Connected")
        startForeground(NOTIFICATION_ID, notification)
        
        setupPeerConnection()
        
        if (call.type == CallType.VIDEO) {
            setupLocalVideo()
        }
        setupLocalAudio()
        
        // Answer the call
        createAnswer()
    }
    
    private fun setupPeerConnection() {
        val rtcConfig = PeerConnection.RTCConfiguration(
            listOf(
                PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer()
            )
        )
        
        peerConnection = peerConnectionFactory?.createPeerConnection(
            rtcConfig,
            object : PeerConnection.Observer {
                override fun onSignalingChange(signalingState: PeerConnection.SignalingState?) {}
                override fun onIceConnectionChange(iceConnectionState: PeerConnection.IceConnectionState?) {}
                override fun onIceConnectionReceivingChange(receiving: Boolean) {}
                override fun onIceGatheringChange(iceGatheringState: PeerConnection.IceGatheringState?) {}
                override fun onIceCandidate(iceCandidate: IceCandidate?) {}
                override fun onIceCandidatesRemoved(iceCandidates: Array<out IceCandidate>?) {}
                override fun onAddStream(mediaStream: MediaStream?) {}
                override fun onRemoveStream(mediaStream: MediaStream?) {}
                override fun onDataChannel(dataChannel: DataChannel?) {}
                override fun onRenegotiationNeeded() {}
                override fun onAddTrack(rtpReceiver: RtpReceiver?, mediaStreams: Array<out MediaStream>?) {}
            }
        )
    }
    
    private fun setupLocalVideo() {
        val videoCapturer = createCameraCapturer()
        val videoSource = peerConnectionFactory?.createVideoSource(false)
        videoCapturer?.initialize(SurfaceTextureHelper.create("CaptureThread", EglBase.create().eglBaseContext), this, videoSource?.capturerObserver)
        
        localVideoTrack = peerConnectionFactory?.createVideoTrack("100", videoSource)
        
        val mediaStream = peerConnectionFactory?.createLocalMediaStream("ARDAMS")
        mediaStream?.addTrack(localVideoTrack)
        peerConnection?.addStream(mediaStream)
    }
    
    private fun setupLocalAudio() {
        val audioConstraints = MediaConstraints()
        val audioSource = peerConnectionFactory?.createAudioSource(audioConstraints)
        localAudioTrack = peerConnectionFactory?.createAudioTrack("101", audioSource)
        
        val mediaStream = peerConnectionFactory?.createLocalMediaStream("ARDAMS")
        mediaStream?.addTrack(localAudioTrack)
        peerConnection?.addStream(mediaStream)
    }
    
    private fun createCameraCapturer(): CameraVideoCapturer? {
        val enumerator = Camera2Enumerator(this)
        val deviceNames = enumerator.deviceNames
        
        // Try to find front camera first
        for (deviceName in deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                return enumerator.createCapturer(deviceName, null)
            }
        }
        
        // Fall back to any camera
        for (deviceName in deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                return enumerator.createCapturer(deviceName, null)
            }
        }
        
        return null
    }
    
    private fun createOffer() {
        val constraints = MediaConstraints()
        peerConnection?.createOffer(object : SdpObserver {
            override fun onCreateSuccess(sessionDescription: SessionDescription?) {
                sessionDescription?.let {
                    peerConnection?.setLocalDescription(object : SdpObserver {
                        override fun onCreateSuccess(p0: SessionDescription?) {}
                        override fun onSetSuccess() {
                            // Send offer to remote peer via signaling server
                        }
                        override fun onCreateFailure(error: String?) {}
                        override fun onSetFailure(error: String?) {}
                    }, it)
                }
            }
            override fun onSetSuccess() {}
            override fun onCreateFailure(error: String?) {}
            override fun onSetFailure(error: String?) {}
        }, constraints)
    }
    
    private fun createAnswer() {
        val constraints = MediaConstraints()
        peerConnection?.createAnswer(object : SdpObserver {
            override fun onCreateSuccess(sessionDescription: SessionDescription?) {
                sessionDescription?.let {
                    peerConnection?.setLocalDescription(object : SdpObserver {
                        override fun onCreateSuccess(p0: SessionDescription?) {}
                        override fun onSetSuccess() {
                            // Send answer to remote peer via signaling server
                        }
                        override fun onCreateFailure(error: String?) {}
                        override fun onSetFailure(error: String?) {}
                    }, it)
                }
            }
            override fun onSetSuccess() {}
            override fun onCreateFailure(error: String?) {}
            override fun onSetFailure(error: String?) {}
        }, constraints)
    }
    
    private fun endCall() {
        peerConnection?.close()
        localVideoTrack?.dispose()
        localAudioTrack?.dispose()
        stopForeground(true)
        stopSelf()
    }
    
    private fun declineCall() {
        endCall()
    }
    
    private fun createCallNotification(title: String, content: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
}