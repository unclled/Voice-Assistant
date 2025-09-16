package com.project.voiceassistant.model.geo

import kotlinx.serialization.Serializable

@Serializable
data class GeoSearchResponse(
    val city: Map<String, GeoObject>? = null,
    val area: List<GeoObject>? = null,
    val rajon: List<GeoObject>? = null,
    val country: List<GeoObject>? = null,

    val limit: Int,
    val balans: Int,
    val status: Int,
    val error: String? = null
)

@Serializable
data class GeoObject(
    val id: Int,
    val name: String,
    val english: String? = null,
    val country: String? = null
)