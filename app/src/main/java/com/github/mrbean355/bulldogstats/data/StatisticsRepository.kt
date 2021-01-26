package com.github.mrbean355.bulldogstats.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.EOFException
import javax.inject.Inject
import javax.inject.Provider

private val cache = mutableMapOf<String, Map<String, Int>>()

class StatisticsRepository @Inject constructor(
    private val service: StatisticsService,
    @AuthToken private val authToken: Provider<String>
) {

    suspend fun listProperties(): List<String> = withContext(IO) {
        cache.clear()
        service.listProperties(authToken.get()).sorted()
    }

    suspend fun getStatistic(property: String): Map<String, Int> = withContext(IO) {
        if (property in cache) {
            cache.getValue(property)
        } else {
            service.getStatistic(property, authToken.get()).also {
                cache[property] = it
            }
        }
    }

    suspend fun countRecentUsers(minutes: Long): Long = withContext(IO) {
        service.getRecentUsers(authToken.get(), minutes)
    }

    suspend fun refreshMods() = withContext(IO) {
        service.refreshMods(authToken.get())
    }

    suspend fun shutDown() = withContext(IO) {
        try {
            service.shutDown(authToken.get())
        } catch (t: Throwable) {
            if (t.cause is EOFException) {
                // Swallow; this means the shut down was successful.
            } else {
                throw t
            }
        }
    }
}