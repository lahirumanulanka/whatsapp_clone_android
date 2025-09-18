package com.example.whatsapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.example.whatsapp.data.models.CallType
import com.example.whatsapp.utils.CallManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToChat: (String) -> Unit,
    onNavigateToIncomingCall: (String) -> Unit,
    onNavigateToOutgoingCall: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val callManager = remember { CallManager(context) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WhatsApp") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF075E54),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                BottomNavItem.values().forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF075E54),
                            selectedTextColor = Color(0xFF075E54),
                            indicatorColor = Color(0xFF075E54).copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> ChatsTab(onNavigateToChat = onNavigateToChat)
                1 -> StatusTab()
                2 -> CallsTab(
                    onNavigateToIncomingCall = onNavigateToIncomingCall,
                    onNavigateToOutgoingCall = onNavigateToOutgoingCall,
                    onSimulateIncomingCall = {
                        val callId = callManager.simulateIncomingCall(
                            callerContactId = "demo_caller",
                            callerName = "Demo Caller",
                            callType = CallType.VOICE
                        )
                        onNavigateToIncomingCall(callId)
                    }
                )
            }
        }
    }
}

enum class BottomNavItem(val title: String, val icon: ImageVector) {
    CHATS("Chats", Icons.Default.Chat),
    STATUS("Status", Icons.Default.RadioButtonChecked),
    CALLS("Calls", Icons.Default.Call)
}