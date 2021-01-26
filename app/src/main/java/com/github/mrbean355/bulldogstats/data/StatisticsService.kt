package com.github.mrbean355.bulldogstats.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StatisticsService {

    @GET("statistics/properties")
    suspend fun listProperties(
        @Query("token") token: String
    ): List<String>

    @GET("statistics/recentUsers")
    suspend fun getRecentUsers(
        @Query("token") token: String,
        @Query("period") period: Long
    ): Long

    @GET("statistics/{property}")
    suspend fun getStatistic(
        @Path("property") property: String,
        @Query("token") token: String
    ): Map<String, Int>

    @GET("mods/refresh")
    suspend fun refreshMods(
        @Query("token") token: String
    )

    @GET("metadata/shutdown")
    suspend fun shutDown(
        @Query("token") token: String
    )

}