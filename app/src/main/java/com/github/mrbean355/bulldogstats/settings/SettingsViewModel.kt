package com.github.mrbean355.bulldogstats.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

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