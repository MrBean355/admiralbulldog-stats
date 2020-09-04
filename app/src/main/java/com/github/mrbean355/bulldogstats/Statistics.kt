package com.github.mrbean355.bulldogstats

data class Statistics(
        val activeUsers: Int,
        val appVersions: Map<String, Int>,
        val platforms: Map<String, Int>,
        val discordBot: Map<String, Int>,
        val topSounds: Map<String, Int>,
        val dotaMod: Map<String, Int>,
        val modVersion: Map<String, Int>,
)

private val cache = Statistics(
        activeUsers = 13,
        appVersions = mapOf(),
        platforms = mapOf(
                "Windows" to 100,
                "Linux" to 5,
                "Mac" to 10
        ),
        discordBot = mapOf(
                "true" to 65,
                "false" to 45
        ),
        topSounds = mapOf(),
        dotaMod = mapOf(
                "true" to 75,
                "false" to 35
        ),
        modVersion = mapOf()
)

fun getStatistics() = cache
