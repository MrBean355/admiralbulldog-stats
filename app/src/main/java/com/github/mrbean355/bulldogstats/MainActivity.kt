package com.github.mrbean355.bulldogstats

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainViewModel().entries.observe(this) {
            pie_chart.data = it
            pie_chart.invalidate()
        }

        pie_chart.apply {
            setUsePercentValues(false)
            description.isEnabled = false

            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            holeRadius = 48f

            transparentCircleRadius = 0f

            centerText = "Platforms"

            isRotationEnabled = false
            isHighlightPerTapEnabled = false

            // entry label styling
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(18f)
        }
    }
}