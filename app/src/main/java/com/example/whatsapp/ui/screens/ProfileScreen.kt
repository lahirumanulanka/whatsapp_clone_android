package com.example.whatsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onEditProfile: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF075E54),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF075E54))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onEditProfile() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        style = MaterialTheme.typography.displaySmall
                    )
                    
                    // Camera icon overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF25D366)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Edit Photo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Your Name",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "+1 (555) 123-4567",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Profile Options
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileOption(
                    icon = Icons.Default.Person,
                    title = "Name",
                    subtitle = "Your Name",
                    onClick = onEditProfile
                )
                
                ProfileOption(
                    icon = Icons.Default.Info,
                    title = "About",
                    subtitle = "Hey there! I am using WhatsApp.",
                    onClick = onEditProfile
                )
                
                ProfileOption(
                    icon = Icons.Default.Phone,
                    title = "Phone",
                    subtitle = "+1 (555) 123-4567",
                    onClick = { /* View phone number */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                
                ProfileOption(
                    icon = Icons.Default.Settings,
                    title = "Settings",
                    subtitle = "Privacy, Security, Help",
                    onClick = onSettingsClick
                )
                
                ProfileOption(
                    icon = Icons.Default.Star,
                    title = "Starred Messages",
                    subtitle = "View your starred messages",
                    onClick = { /* Navigate to starred messages */ }
                )
                
                ProfileOption(
                    icon = Icons.Default.Block,
                    title = "Blocked Contacts",
                    subtitle = "Manage blocked contacts",
                    onClick = { /* Navigate to blocked contacts */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                
                ProfileOption(
                    icon = Icons.Default.Share,
                    title = "Invite Friends",
                    subtitle = "Share WhatsApp with friends",
                    onClick = { /* Share app */ }
                )
                
                ProfileOption(
                    icon = Icons.Default.Help,
                    title = "Help",
                    subtitle = "FAQ, Contact us, Privacy policy",
                    onClick = { /* Navigate to help */ }
                )
            }
        }
    }
}

@Composable
private fun ProfileOption(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF075E54)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )
        }
        
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}