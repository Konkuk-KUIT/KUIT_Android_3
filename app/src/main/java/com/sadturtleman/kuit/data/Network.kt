package com.sadturtleman.kuit.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

// 서버 주소 + JSON 변환기. 앱에 하나만.


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        }.build())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    fun provideBookService(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)
}
