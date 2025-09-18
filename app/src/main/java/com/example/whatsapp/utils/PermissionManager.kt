package com.example.whatsapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: ComponentActivity) {
    
    companion object {
        val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        
        val STORAGE_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        
        val LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        
        val PHONE_PERMISSIONS = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        )
        
        val CONTACTS_PERMISSIONS = arrayOf(
            Manifest.permission.READ_CONTACTS
        )
        
        val MICROPHONE_PERMISSIONS = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )
    }
    
    private var onPermissionResult: ((Boolean) -> Unit)? = null
    
    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.values.all { it }
            onPermissionResult?.invoke(allGranted)
        }
    
    fun requestCameraPermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(CAMERA_PERMISSIONS)
    }
    
    fun requestStoragePermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(STORAGE_PERMISSIONS)
    }
    
    fun requestLocationPermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(LOCATION_PERMISSIONS)
    }
    
    fun requestPhonePermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(PHONE_PERMISSIONS)
    }
    
    fun requestContactsPermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(CONTACTS_PERMISSIONS)
    }
    
    fun requestMicrophonePermission(onResult: (Boolean) -> Unit) {
        onPermissionResult = onResult
        permissionLauncher.launch(MICROPHONE_PERMISSIONS)
    }
    
    fun hasCameraPermission(): Boolean {
        return hasPermissions(CAMERA_PERMISSIONS)
    }
    
    fun hasStoragePermission(): Boolean {
        return hasPermissions(STORAGE_PERMISSIONS)
    }
    
    fun hasLocationPermission(): Boolean {
        return hasPermissions(LOCATION_PERMISSIONS)
    }
    
    fun hasPhonePermission(): Boolean {
        return hasPermissions(PHONE_PERMISSIONS)
    }
    
    fun hasContactsPermission(): Boolean {
        return hasPermissions(CONTACTS_PERMISSIONS)
    }
    
    fun hasMicrophonePermission(): Boolean {
        return hasPermissions(MICROPHONE_PERMISSIONS)
    }
    
    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}