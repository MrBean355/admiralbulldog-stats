package com.github.mrbean355.bulldogstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            val stats = statisticsRepository.getStats()
            loading.value = false
            recentUsers.value = stats.recentUsers
            dailyUsers.value = stats.dailyUsers
            properties.value = stats.properties.keys.toList()
        }
    }
}
