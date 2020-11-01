package com.github.mrbean355.bulldogstats.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val statisticsRepository = StatisticsRepository()

    fun onRefreshModsClicked(onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                statisticsRepository.refreshMods()
                onComplete(true)
            } catch (t: Throwable) {
                Log.e("SettingsViewModel", "Error refreshing mods", t)
                onComplete(false)
            }
        }
    }

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