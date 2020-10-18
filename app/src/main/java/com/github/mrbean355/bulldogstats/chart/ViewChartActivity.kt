package com.github.mrbean355.bulldogstats.chart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mrbean355.bulldogstats.BackButtonActivity
import com.github.mrbean355.bulldogstats.R
import com.github.mrbean355.bulldogstats.StatisticsAdapter
import kotlinx.android.synthetic.main.activity_view_chart.*

private const val SLICE_SPACE_DP = 4f
private const val VALUE_TEXT_SIZE_DP = 18f
private const val ANIM_DURATION_MS = 750
private const val KEY_PROPERTY_KEY = "PROPERTY_KEY"

class ViewChartActivity : BackButtonActivity(R.layout.activity_view_chart) {
    private val viewModel by viewModels<ViewChartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pie_chart.description.isEnabled = false
        pie_chart.legend.isEnabled = false
        pie_chart.setEntryLabelTextSize(16f)
        pie_chart.isHighlightPerTapEnabled = false

        val adapter = StatisticsAdapter(horizontal = true)
        breakdown.adapter = adapter

        val key = intent.getStringExtra(KEY_PROPERTY_KEY).orEmpty()
        title = key

        viewModel.initialise(key)
        viewModel.breakdown.observe(this) {
            adapter.submitList(it)
        }
        viewModel.properties.observe(this) { data ->
            pie_chart.data = PieData(PieDataSet(
                    data.map { PieEntry(it.value.toFloat(), it.key) }, key
            ).also {
                it.colors = ColorTemplate.COLORFUL_COLORS.toList()
                it.sliceSpace = SLICE_SPACE_DP
                it.valueTextSize = VALUE_TEXT_SIZE_DP
                it.valueFormatter = LargeValueFormatter()
            })
            pie_chart.animateY(ANIM_DURATION_MS)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

@Suppress("FunctionName")
fun ViewChartActivity(context: Context, key: String): Intent {
    return Intent(context, ViewChartActivity::class.java)
            .putExtra(KEY_PROPERTY_KEY, key)
}