package com.example.kubrick.data.remote

import com.example.kubrick.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbClient {

    private const val BASE_URL =
        "https://api.themoviedb.org/3/"

    private val authInterceptor = Interceptor { chain ->

        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}"
            )
            .addHeader("accept", "application/json")
            .build()

        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: TmdbApi by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(TmdbApi::class.java)
    }
}