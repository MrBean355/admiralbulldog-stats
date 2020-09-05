package com.github.mrbean355.bulldogstats

import android.app.Application

class StatisticsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application = instance!!

    }
}