package com.github.mrbean355.bulldogstats.data

import android.app.Application
import androidx.preference.PreferenceManager
import com.github.mrbean355.bulldogstats.BuildConfig
import com.github.mrbean355.bulldogstats.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthToken

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideStatisticsService(): StatisticsService = Retrofit.Builder()
        .baseUrl(BuildConfig.HOST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Provides
    @AuthToken
    fun provideAuthToken(application: Application): String =
        PreferenceManager.getDefaultSharedPreferences(application)
            .getString(application.getString(R.string.key_pref_token), null).orEmpty()

}