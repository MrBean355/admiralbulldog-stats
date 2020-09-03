package com.github.mrbean355.bulldogstats

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel {
    val entries = MutableLiveData<PieData>()

    init {
        MainScope().launch {
            delay(2_000)
            entries.value = PieData(PieDataSet(
                    listOf(
                            PieEntry(12f, "Windows"),
                            PieEntry(12f, "Mac"),
                            PieEntry(12f, "Linux"),
                    ),
                    "Platforms"
            ).also {
                it.colors = ColorTemplate.LIBERTY_COLORS.toList()
                it.setDrawValues(false)
            })
        }
    }
}
