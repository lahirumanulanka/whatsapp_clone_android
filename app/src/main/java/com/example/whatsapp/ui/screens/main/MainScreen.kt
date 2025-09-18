package com.example.whatsapp.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.whatsapp.ui.screens.main.tabs.CallsTab
import com.example.whatsapp.ui.screens.main.tabs.ChatsTab
import com.example.whatsapp.ui.screens.main.tabs.StatusTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToChat: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = { 
                    Text(
                        text = "WhatsApp",
                        color = Color.White
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF075E54)
                ),
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
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
            
            // Tab Row
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color(0xFF075E54),
                contentColor = Color.White
            ) {
                val tabs = listOf("CHATS", "STATUS", "CALLS")
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { 
                            Text(
                                text = title,
                                color = Color.White
                            ) 
                        }
                    )
                }
            }
            
            // Tab Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> ChatsTab(onNavigateToChat = onNavigateToChat)
                    1 -> StatusTab()
                    2 -> CallsTab()
                }
            }
        }
        
        // Floating Action Button
        FloatingActionButton(
            onClick = {
                when (pagerState.currentPage) {
                    0 -> { /* New chat */ }
                    1 -> { /* Add status */ }
                    2 -> { /* New call */ }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF25D366)
        ) {
            Icon(
                imageVector = when (pagerState.currentPage) {
                    0 -> Icons.Default.Chat
                    1 -> Icons.Default.CameraAlt
                    2 -> Icons.Default.Call
                    else -> Icons.Default.Add
                },
                contentDescription = when (pagerState.currentPage) {
                    0 -> "New chat"
                    1 -> "Add status"
                    2 -> "New call"
                    else -> "Add"
                },
                tint = Color.White
            )
        }
    }
}