package com.github.mrbean355.bulldogstats.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val recentUsers = MutableLiveData<Long>()
    val dailyUsers = MutableLiveData<Long>()
    val monthlyUsers = MutableLiveData<Long>()
    val properties = MutableLiveData<List<String>>()

    init {
        loadStatistics()
    }

    fun onRefreshClicked() {
        viewModelScope.launch {
            loadStatistics()
        }
    }

    private fun loadStatistics() {
        loading.value = true
        viewModelScope.launch {
            try {
                properties.value = statisticsRepository.listProperties()
                recentUsers.value = statisticsRepository.countRecentUsers(5)
                dailyUsers.value = statisticsRepository.countRecentUsers(TimeUnit.DAYS.toMinutes(1))
                monthlyUsers.value = statisticsRepository.countRecentUsers(TimeUnit.DAYS.toMinutes(30))
            } catch (t: Throwable) {
                Log.e("MainViewModel", "Error getting stats", t)
            }
            loading.value = false
        }
    }
}
