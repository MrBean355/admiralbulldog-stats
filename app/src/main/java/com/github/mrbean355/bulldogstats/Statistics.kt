package com.github.mrbean355.bulldogstats

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlin.random.Random

data class Statistics(
        val activeUsers: Int,
        val appVersions: Map<String, Int>,
        val platforms: Map<String, Int>,
        val discordBot: Map<String, Int>,
        val topSounds: Map<String, Int>,
        val dotaMod: Map<String, Int>,
        val modVersion: Map<String, Int>,
)

suspend fun loadStatistics(): Statistics = withContext(IO) {
    Statistics(
            activeUsers = Random.nextInt(1, 30),
            appVersions = mapOf(),
            platforms = mapOf(),
            discordBot = mapOf(),
            topSounds = mapOf(),
            dotaMod = mapOf(),
            modVersion = mapOf(),
    )
}
