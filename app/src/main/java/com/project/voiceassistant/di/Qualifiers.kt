package com.project.voiceassistant.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeoClient