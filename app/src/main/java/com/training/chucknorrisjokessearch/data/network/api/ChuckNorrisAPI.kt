package com.training.chucknorrisjokessearch.data.network.api

import com.google.gson.GsonBuilder
import com.training.chucknorrisjokessearch.BuildConfig
import com.training.chucknorrisjokessearch.data.network.model.ModelJokesSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 * Powered by:
 * https://rapidapi.com/matchilling/api/chuck-norris/
 */

interface ChuckNorrisAPI {
    @GET("jokes/search")
    suspend fun jokesSearch(
        @Query("query") query: String,
    ): ModelJokesSearchResponse

    companion object {
        fun create(): ChuckNorrisAPI {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ChuckNorrisAPI::class.java)
        }
    }
}