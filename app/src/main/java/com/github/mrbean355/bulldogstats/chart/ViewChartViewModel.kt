package com.github.mrbean355.bulldogstats.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import kotlinx.coroutines.launch

class ViewChartViewModel : ViewModel() {
    private val statisticsRepository = StatisticsRepository()

    val loading = MutableLiveData<Boolean>()
    val breakdown = MutableLiveData<List<String>>()
    val properties = MutableLiveData<Map<String, Int>>()

    fun initialise(key: String) {
        loading.value = true
        viewModelScope.launch {
            val data = statisticsRepository.getStatistic(key)
            properties.value = data
            breakdown.value = data.keys
                    .sortedByDescending { data[it] }
                    .map { "${it.toLowerCase().capitalize()}: ${data[it]}" }

            loading.value = false
        }
    }
}