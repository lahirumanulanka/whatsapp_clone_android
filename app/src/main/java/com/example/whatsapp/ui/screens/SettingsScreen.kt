package com.example.whatsapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var autoDownload by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
        ) {
            // Account Section
            SettingsSection(title = "Account") {
                SettingsItem(
                    icon = Icons.Default.Security,
                    title = "Privacy",
                    subtitle = "Block contacts, disappearing messages",
                    onClick = { /* Navigate to privacy settings */ }
                )
                
                SettingsItem(
                    icon = Icons.Default.Security,
                    title = "Security",
                    subtitle = "End-to-end encryption, verification",
                    onClick = { /* Navigate to security settings */ }
                )
                
                SettingsItem(
                    icon = Icons.Default.AccountCircle,
                    title = "Account",
                    subtitle = "Change number, delete account",
                    onClick = { /* Navigate to account settings */ }
                )
            }
            
            // Chats Section
            SettingsSection(title = "Chats") {
                SettingsItem(
                    icon = Icons.Default.ChatBubble,
                    title = "Chat Backup",
                    subtitle = "Backup to Google Drive",
                    onClick = { /* Navigate to backup settings */ }
                )
                
                SettingsItem(
                    icon = Icons.Default.History,
                    title = "Chat History",
                    subtitle = "Export, clear, delete chats",
                    onClick = { /* Navigate to chat history */ }
                )
                
                SettingsToggleItem(
                    icon = Icons.Default.DarkMode,
                    title = "Dark Theme",
                    subtitle = "Enable dark mode",
                    isChecked = isDarkTheme,
                    onToggle = { isDarkTheme = it }
                )
            }
            
            // Notifications Section
            SettingsSection(title = "Notifications") {
                SettingsToggleItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Message, group & call tones",
                    isChecked = notificationsEnabled,
                    onToggle = { notificationsEnabled = it }
                )
                
                SettingsItem(
                    icon = Icons.Default.VolumeUp,
                    title = "Notification Tone",
                    subtitle = "Default (WhatsApp)",
                    onClick = { /* Open tone picker */ }
                )
            }
            
            // Storage Section
            SettingsSection(title = "Storage and Data") {
                SettingsItem(
                    icon = Icons.Default.Storage,
                    title = "Storage Usage",
                    subtitle = "Network usage, auto-download",
                    onClick = { /* Navigate to storage settings */ }
                )
                
                SettingsToggleItem(
                    icon = Icons.Default.Download,
                    title = "Auto-download",
                    subtitle = "Photos, videos, documents",
                    isChecked = autoDownload,
                    onToggle = { autoDownload = it }
                )
            }
            
            // Help Section
            SettingsSection(title = "Support") {
                SettingsItem(
                    icon = Icons.Default.Help,
                    title = "Help",
                    subtitle = "FAQ, contact us",
                    onClick = { /* Navigate to help */ }
                )
                
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "App Info",
                    subtitle = "Version 2.23.24.14",
                    onClick = { /* Show app info */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF075E54)
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        content()
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SettingsItem(
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
            tint = Color.Gray
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
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

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )
        }
        
        Switch(
            checked = isChecked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF075E54),
                checkedTrackColor = Color(0xFF075E54).copy(alpha = 0.3f)
            )
        )
    }
}