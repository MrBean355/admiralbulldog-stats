package com.github.mrbean355.bulldogstats

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pie_chart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            isDrawHoleEnabled = false

            setEntryLabelTextSize(14f)
            setEntryLabelColor(Color.BLACK)
        }

        platforms.setOnClickListener {
            renderGraph(getStatistics().platforms)
        }
        discord_bot.setOnClickListener {
            renderGraph(getStatistics().discordBot)
        }
        dota_mod.setOnClickListener {
            renderGraph(getStatistics().dotaMod)
        }
    }

    private fun renderGraph(data: Map<String, Int>) {
        val entries = data.map {
            PieEntry(it.value.toFloat(), it.key)
        }
        pie_chart.data = PieData(PieDataSet(entries, "Platforms").also {
            it.colors = ColorTemplate.MATERIAL_COLORS.toList()
            it.valueTextSize = 18f
            it.sliceSpace = 4f
        })
        pie_chart.animateY(1_000)
    }
}