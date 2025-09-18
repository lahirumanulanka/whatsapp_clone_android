package com.example.whatsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MediaAttachmentBottomSheet(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onDocumentClick: () -> Unit,
    onLocationClick: () -> Unit,
    onContactClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Share",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MediaOption(
                icon = Icons.Default.Camera,
                label = "Camera",
                backgroundColor = Color(0xFFE91E63),
                onClick = onCameraClick
            )
            
            MediaOption(
                icon = Icons.Default.Photo,
                label = "Gallery",
                backgroundColor = Color(0xFF9C27B0),
                onClick = onGalleryClick
            )
            
            MediaOption(
                icon = Icons.Default.Description,
                label = "Document",
                backgroundColor = Color(0xFF3F51B5),
                onClick = onDocumentClick
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MediaOption(
                icon = Icons.Default.LocationOn,
                label = "Location",
                backgroundColor = Color(0xFF4CAF50),
                onClick = onLocationClick
            )
            
            MediaOption(
                icon = Icons.Default.Person,
                label = "Contact",
                backgroundColor = Color(0xFF2196F3),
                onClick = onContactClick
            )
            
            // Empty space to balance the row
            Spacer(modifier = Modifier.size(80.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MediaOption(
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            containerColor = backgroundColor
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun VoiceRecordingIndicator(
    isRecording: Boolean,
    duration: String,
    onStopRecording: () -> Unit,
    onCancelRecording: () -> Unit
) {
    if (isRecording) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E8)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Recording indicator
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Red, androidx.compose.foundation.shape.CircleShape)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Recording... $duration",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF075E54)
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                IconButton(onClick = onCancelRecording) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = Color.Gray
                    )
                }
                
                IconButton(onClick = onStopRecording) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color(0xFF075E54)
                    )
                }
            }
        }
    }
}

@Composable
fun AudioMessageBubble(
    duration: String,
    isPlaying: Boolean,
    progress: Float,
    onPlayPause: () -> Unit,
    isFromCurrentUser: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFromCurrentUser) Color(0xFFDCF8C6) else Color.White
        ),
        modifier = Modifier.widthIn(min = 200.dp, max = 280.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color(0xFF075E54)
                )
            }
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF075E54),
                    trackColor = Color.Gray.copy(alpha = 0.3f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ImageMessageBubble(
    imageUrl: String,
    caption: String?,
    onImageClick: () -> Unit,
    isFromCurrentUser: Boolean
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFromCurrentUser) Color(0xFFDCF8C6) else Color.White
        ),
        modifier = Modifier
            .widthIn(max = 280.dp)
            .clickable { onImageClick() }
    ) {
        Column {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Image",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Gray
                )
            }
            
            if (!caption.isNullOrBlank()) {
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}