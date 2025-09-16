package com.project.voiceassistant.di

import android.content.Context
import androidx.room.Room
import com.project.voiceassistant.db.AppDatabase
import com.project.voiceassistant.db.dao.MessageDao
import com.project.voiceassistant.network.geo.GeoApiClient
import com.project.voiceassistant.network.geo.GeoApiService
import com.project.voiceassistant.network.geo.GeoApiServiceImpl
import com.project.voiceassistant.network.weather.ApiServiceImpl
import com.project.voiceassistant.network.weather.WeatherApiClient
import com.project.voiceassistant.network.weather.WeatherApiService
import com.project.voiceassistant.repository.db.MessageRepository
import com.project.voiceassistant.repository.db.MessageRepositoryImpl
import com.project.voiceassistant.repository.geo.GeoRepository
import com.project.voiceassistant.repository.geo.GeoRepositoryImpl
import com.project.voiceassistant.repository.weather.WeatherRepository
import com.project.voiceassistant.repository.weather.WeatherRepositoryImpl
import com.project.voiceassistant.usecase.Ai
import com.project.voiceassistant.utils.TextToSpeechService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.withCharset
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository {
        return MessageRepositoryImpl(messageDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "voice_assistant_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao {
        return appDatabase.messageDao()
    }

    @Provides
    @Singleton
    fun provideTextToSpeechService(@ApplicationContext context: Context): TextToSpeechService {
        return TextToSpeechService(context)
    }

    @Provides
    @Singleton
    fun provideAi(
        @ApplicationContext context: Context,
        weatherRepository: WeatherRepository,
        geoRepository: GeoRepository
    ): Ai {
        return Ai(context, weatherRepository, geoRepository)
    }

    @Provides
    @Singleton
    @WeatherClient
    fun provideWeatherHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) { level = LogLevel.ALL }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.openweathermap.org"
                }
            }
        }
    }

    @Provides
    @Singleton
    @GeoClient
    fun provideGeoHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) { level = LogLevel.ALL }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "htmlweb.ru"
                }
                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
            }
        }
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(weatherApiClient: WeatherApiClient): WeatherApiService {
        return ApiServiceImpl(weatherApiClient)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: WeatherApiService,
        @ApplicationContext context: Context
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, context)
    }

    @Provides
    @Singleton
    fun provideWeatherApiClient(@WeatherClient httpClient: HttpClient): WeatherApiClient {
        return WeatherApiClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideGeoApiClient(@GeoClient httpClient: HttpClient): GeoApiClient {
        return GeoApiClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideGeoApiService(geoApiClient: GeoApiClient): GeoApiService {
        return GeoApiServiceImpl(geoApiClient)
    }

    @Provides
    @Singleton
    fun provideGeoRepository(geoApiService: GeoApiService): GeoRepository {
        return GeoRepositoryImpl(geoApiService)
    }
}