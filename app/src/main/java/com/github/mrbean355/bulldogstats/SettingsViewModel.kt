package com.github.mrbean355.bulldogstats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val statisticsRepository = StatisticsRepository()

    fun onShutDownClicked(onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                statisticsRepository.shutDown()
                onComplete(true)
            } catch (t: Throwable) {
                Log.e("SettingsViewModel", "Error shutting down", t)
                onComplete(false)
            }
        }
    }
}