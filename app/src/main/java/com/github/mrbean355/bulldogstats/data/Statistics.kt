package com.github.mrbean355.bulldogstats.data

import androidx.preference.PreferenceManager
import com.github.mrbean355.bulldogstats.BuildConfig
import com.github.mrbean355.bulldogstats.R
import com.github.mrbean355.bulldogstats.StatisticsApplication
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.EOFException

private val cache = mutableMapOf<String, Map<String, Int>>()

class StatisticsRepository {
    private val service = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<StatisticsService>()

    suspend fun listProperties(): List<String> = withContext(IO) {
        cache.clear()
        service.listProperties(loadToken()).sorted()
    }

    suspend fun getStatistic(property: String): Map<String, Int> = withContext(IO) {
        if (property in cache) {
            cache.getValue(property)
        } else {
            service.getStatistic(property, loadToken()).also {
                cache[property] = it
            }
        }
    }

    suspend fun countRecentUsers(minutes: Long): Long = withContext(IO) {
        service.getRecentUsers(loadToken(), minutes)
    }

    suspend fun refreshMods() = withContext(IO) {
        service.refreshMods(loadToken())
    }

    suspend fun shutDown() = withContext(IO) {
        try {
            service.shutDown(loadToken())
        } catch (t: Throwable) {
            if (t.cause is EOFException) {
                // Swallow; this means the shut down was successful.
            } else {
                throw t
            }
        }
    }
}

private fun loadToken(): String {
    val app = StatisticsApplication.getInstance()
    return PreferenceManager.getDefaultSharedPreferences(app)
            .getString(app.getString(R.string.key_pref_token), null).orEmpty()
}

private interface StatisticsService {

    @GET("statistics/properties")
    suspend fun listProperties(@Query("token") token: String): List<String>

    @GET("statistics/recentUsers")
    suspend fun getRecentUsers(@Query("token") token: String, @Query("period") period: Long): Long

    @GET("statistics/{property}")
    suspend fun getStatistic(@Path("property") property: String, @Query("token") token: String): Map<String, Int>

    @GET("mods/refresh")
    suspend fun refreshMods(@Query("token") token: String)

    @GET("metadata/shutdown")
    suspend fun shutDown(@Query("token") token: String)

}
