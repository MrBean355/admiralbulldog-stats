package com.github.mrbean355.bulldogstats.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.github.mrbean355.bulldogstats.R
import com.github.mrbean355.bulldogstats.StatisticsAdapter
import com.github.mrbean355.bulldogstats.chart.ViewChartActivity
import com.github.mrbean355.bulldogstats.formatProperty
import com.github.mrbean355.bulldogstats.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = StatisticsAdapter(formatter = String::formatProperty) {
            startActivity(ViewChartActivity(this, it))
        }
        statistics.adapter = adapter
        statistics.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        refresh_fab.setOnClickListener {
            viewModel.onRefreshClicked()
        }

        viewModel.loading.observe(this) {
            progress_bar.isVisible = it
        }
        viewModel.recentUsers.observe(this) {
            stats_heading.isVisible = true
            recent_users.text = getString(R.string.label_recent_users, it)
        }
        viewModel.dailyUsers.observe(this) {
            daily_users.text = getString(R.string.label_daily_users, it)
        }
        viewModel.monthlyUsers.observe(this) {
            monthly_users.text = getString(R.string.label_monthly_users, it)
        }
        viewModel.properties.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}