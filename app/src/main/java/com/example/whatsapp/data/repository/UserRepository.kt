package com.example.whatsapp.data.repository

import com.example.whatsapp.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: Flow<User?> = _currentUser.asStateFlow()
    
    private val _contacts = MutableStateFlow<List<User>>(emptyList())
    val contacts: Flow<List<User>> = _contacts.asStateFlow()
    
    init {
        initializeMockData()
    }
    
    suspend fun login(phoneNumber: String): User? {
        // Mock login - in real app, this would authenticate with backend
        val user = User(
            id = "user1",
            name = "Your Name",
            phoneNumber = phoneNumber,
            profileImageUrl = "",
            status = "Hey there! I am using WhatsApp.",
            isOnline = true,
            lastSeen = System.currentTimeMillis()
        )
        
        _currentUser.value = user
        return user
    }
    
    suspend fun logout() {
        _currentUser.value = null
    }
    
    suspend fun updateUserStatus(status: String) {
        _currentUser.value?.let { user ->
            _currentUser.value = user.copy(status = status)
        }
    }
    
    suspend fun updateUserProfile(name: String, profileImageUrl: String) {
        _currentUser.value?.let { user ->
            _currentUser.value = user.copy(
                name = name,
                profileImageUrl = profileImageUrl
            )
        }
    }
    
    suspend fun getUserById(userId: String): User? {
        // Check if it's current user
        _currentUser.value?.let { currentUser ->
            if (currentUser.id == userId) return currentUser
        }
        
        // Check contacts
        return _contacts.value.find { it.id == userId }
    }
    
    suspend fun searchContacts(query: String): List<User> {
        return _contacts.value.filter { user ->
            user.name.contains(query, ignoreCase = true) ||
            user.phoneNumber.contains(query)
        }
    }
    
    suspend fun addContact(user: User) {
        val currentContacts = _contacts.value.toMutableList()
        if (!currentContacts.any { it.id == user.id }) {
            currentContacts.add(user)
            _contacts.value = currentContacts
        }
    }
    
    suspend fun removeContact(userId: String) {
        val currentContacts = _contacts.value.toMutableList()
        currentContacts.removeAll { it.id == userId }
        _contacts.value = currentContacts
    }
    
    suspend fun updateUserOnlineStatus(userId: String, isOnline: Boolean) {
        val currentContacts = _contacts.value.map { user ->
            if (user.id == userId) {
                user.copy(
                    isOnline = isOnline,
                    lastSeen = if (!isOnline) System.currentTimeMillis() else user.lastSeen
                )
            } else {
                user
            }
        }
        _contacts.value = currentContacts
    }
    
    fun isLoggedIn(): Boolean {
        return _currentUser.value != null
    }
    
    fun getCurrentUserId(): String? {
        return _currentUser.value?.id
    }
    
    private fun initializeMockData() {
        val mockContacts = listOf(
            User(
                id = "user2",
                name = "Alice Johnson",
                phoneNumber = "+1234567890",
                profileImageUrl = "",
                status = "Busy at work",
                isOnline = true,
                lastSeen = System.currentTimeMillis() - 300000 // 5 minutes ago
            ),
            User(
                id = "user3",
                name = "Bob Smith",
                phoneNumber = "+1234567891",
                profileImageUrl = "",
                status = "Available",
                isOnline = false,
                lastSeen = System.currentTimeMillis() - 3600000 // 1 hour ago
            ),
            User(
                id = "user4",
                name = "Carol Davis",
                phoneNumber = "+1234567892",
                profileImageUrl = "",
                status = "In a meeting",
                isOnline = true,
                lastSeen = System.currentTimeMillis()
            ),
            User(
                id = "user5",
                name = "David Wilson",
                phoneNumber = "+1234567893",
                profileImageUrl = "",
                status = "Sleeping",
                isOnline = false,
                lastSeen = System.currentTimeMillis() - 28800000 // 8 hours ago
            ),
            User(
                id = "user6",
                name = "Emma Brown",
                phoneNumber = "+1234567894",
                profileImageUrl = "",
                status = "At the gym",
                isOnline = true,
                lastSeen = System.currentTimeMillis() - 600000 // 10 minutes ago
            )
        )
        
        _contacts.value = mockContacts
    }
}