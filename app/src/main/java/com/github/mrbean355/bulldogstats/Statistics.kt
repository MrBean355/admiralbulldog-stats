package com.github.mrbean355.bulldogstats

import android.app.Application
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

data class StatisticsResponse(
        val recentUsers: Int,
        val dailyUsers: Int,
        val properties: Map<String, Map<String, Int>>
)

lateinit var application: Application
private val lock = Mutex()
private var cache: StatisticsResponse? = null

class StatisticsRepository {
    private val service = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<StatisticsService>()

    suspend fun getStats(): StatisticsResponse = withContext(IO) {
        lock.withLock {
            var localCache = cache
            if (localCache == null) {
                val token = PreferenceManager.getDefaultSharedPreferences(application).getString(application.getString(R.string.key_pref_token), null).orEmpty()
                localCache = service.getStatistics(token)
                cache = localCache
            }
            localCache
        }
    }

    suspend fun getProperties(key: String): Map<String, Int> = withContext(IO) {
        getStats().properties.getValue(key)
    }

    suspend fun invalidate() {
        lock.withLock {
            cache = null
        }
    }
}

private interface StatisticsService {

    @GET("statistics/get")
    suspend fun getStatistics(@Query("token") token: String): StatisticsResponse

}
