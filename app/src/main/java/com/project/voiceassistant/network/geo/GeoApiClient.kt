package com.project.voiceassistant.network.geo

import com.project.voiceassistant.BuildConfig
import com.project.voiceassistant.di.GeoClient
import com.project.voiceassistant.model.geo.GeoSearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class GeoApiClient @Inject constructor(
    @GeoClient private val httpClient: HttpClient
) {
    private val apiKey = BuildConfig.HTMLWEB_API_KEY

    suspend fun search(query: String): GeoSearchResponse {
        return httpClient.get("json/geo/search") {
            parameter("search", query)
            parameter("api_key", apiKey)
        }.body<GeoSearchResponse>()
    }
}