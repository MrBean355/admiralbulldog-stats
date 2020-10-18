package com.github.mrbean355.bulldogstats.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val statisticsRepository = StatisticsRepository()

    val loading = MutableLiveData<Boolean>()
    val recentUsers = MutableLiveData<Int>()
    val dailyUsers = MutableLiveData<Int>()
    val properties = MutableLiveData<List<String>>()

    init {
        loadStatistics()
    }

    fun onRefreshClicked() {
        viewModelScope.launch {
            statisticsRepository.invalidate()
            loadStatistics()
        }
    }

    private fun loadStatistics() {
        loading.value = true
        viewModelScope.launch {
            try {
                val stats = statisticsRepository.getStats()
                recentUsers.value = stats.recentUsers
                dailyUsers.value = stats.dailyUsers
                properties.value = stats.properties.keys.toList()
            } catch (t: Throwable) {
                Log.e("MainViewModel", "Error getting stats", t)
            }
            loading.value = false
        }
    }
}
