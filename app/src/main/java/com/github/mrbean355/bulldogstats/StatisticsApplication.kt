package com.github.mrbean355.bulldogstats

import android.app.Application

class StatisticsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}