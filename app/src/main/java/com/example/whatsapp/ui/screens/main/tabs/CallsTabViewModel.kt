package com.example.whatsapp.ui.screens.main.tabs

import androidx.lifecycle.ViewModel
import com.example.whatsapp.call.CallManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CallsTabViewModel @Inject constructor(
    val callManager: CallManager
) : ViewModel()