package com.github.mrbean355.bulldogstats.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mrbean355.bulldogstats.data.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewChartViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

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